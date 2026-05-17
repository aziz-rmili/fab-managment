package com.gestion.fabrication.controller;

import com.gestion.fabrication.dto.OrdreFabricationDTO;
import com.gestion.fabrication.service.OrdreFabricationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Contrôleur OrdreFabrication.
 *
 * Slide 26 du cours :
 * "L'API renvoie un DTO au lieu d'une entité"
 *
 * Exemple body JSON pour créer un ordre :
 * {
 *   "projet": "Projet Alpha",
 *   "quantite": 50,
 *   "date": "2026-04-04",
 *   "etat": "EN_ATTENTE",
 *   "produitId": 1,
 *   "machineId": 2,
 *   "employeId": 3
 * }
 */
@RestController
@RequestMapping("/api/ordres")
@CrossOrigin(origins = "*")
@Tag(name = "Ordres de Fabrication", description = "Gestion des ordres de fabrication")
public class OrdreFabricationController {

    private final OrdreFabricationService ordreService;

    public OrdreFabricationController(OrdreFabricationService ordreService) {
        this.ordreService = ordreService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les ordres")
    public List<OrdreFabricationDTO> getAll() {
        return ordreService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un ordre par son ID")
    public ResponseEntity<OrdreFabricationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ordreService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel ordre de fabrication")
    public ResponseEntity<OrdreFabricationDTO> create(@Valid @RequestBody OrdreFabricationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordreService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un ordre existant")
    public ResponseEntity<OrdreFabricationDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody OrdreFabricationDTO dto) {
        return ResponseEntity.ok(ordreService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un ordre")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ordreService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/etat")
    @Operation(summary = "Changer l'état d'un ordre (EN_ATTENTE → EN_COURS → TERMINE / ANNULE)")
    public ResponseEntity<OrdreFabricationDTO> changerEtat(@PathVariable Long id,
                                                            @RequestBody String nouvelEtat) {
        return ResponseEntity.ok(ordreService.changerEtat(id, nouvelEtat.replace("\"", "")));
    }

    @GetMapping("/etat/{etat}")
    @Operation(summary = "Filtrer les ordres par état")
    public List<OrdreFabricationDTO> getByEtat(@PathVariable String etat) {
        return ordreService.getByEtat(etat);
    }

    @GetMapping("/produit/{produitId}")
    @Operation(summary = "Lister les ordres d'un produit")
    public List<OrdreFabricationDTO> getByProduit(@PathVariable Long produitId) {
        return ordreService.getByProduit(produitId);
    }
}
