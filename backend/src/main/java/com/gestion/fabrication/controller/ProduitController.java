package com.gestion.fabrication.controller;

import com.gestion.fabrication.dto.ProduitDTO;
import com.gestion.fabrication.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Contrôleur Produit — API REST.
 *
 * Cours du prof slide 14 :
 * "@RestController : Indiquer à Spring que cette classe est un bean
 *  + insérer le retour de la méthode au format JSON dans le corps HTTP"
 *
 * Slide 26 : "L'API renvoie un DTO au lieu d'une entité"
 * → toutes les méthodes renvoient des ProduitDTO, jamais des Produit entité.
 *
 * Ce contrôleur est volontairement MINCE (thin controller) :
 * toute la logique est dans ProduitService.
 */
@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "*")
@Tag(name = "Produits", description = "Gestion des produits fabriqués")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les produits")
    public List<ProduitDTO> getAll() {
        return produitService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un produit par son ID")
    public ResponseEntity<ProduitDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau produit")
    public ResponseEntity<ProduitDTO> create(@Valid @RequestBody ProduitDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un produit existant")
    public ResponseEntity<ProduitDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody ProduitDTO dto) {
        return ResponseEntity.ok(produitService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Filtrer les produits par type")
    public List<ProduitDTO> getByType(@PathVariable String type) {
        return produitService.getByType(type);
    }

    @GetMapping("/stock-faible/{seuil}")
    @Operation(summary = "Produits avec stock inférieur au seuil")
    public List<ProduitDTO> getStockFaible(@PathVariable int seuil) {
        return produitService.getStockFaible(seuil);
    }
}
