package com.gestion.fabrication.controller;

import com.gestion.fabrication.dto.MachineDTO;
import com.gestion.fabrication.service.MachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/machines")
@CrossOrigin(origins = "*")
@Tag(name = "Machines", description = "Gestion des machines de production")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les machines")
    public List<MachineDTO> getAll() {
        return machineService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une machine par son ID")
    public ResponseEntity<MachineDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(machineService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle machine")
    public ResponseEntity<MachineDTO> create(@Valid @RequestBody MachineDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(machineService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une machine")
    public ResponseEntity<MachineDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody MachineDTO dto) {
        return ResponseEntity.ok(machineService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une machine")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        machineService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/etat/{etat}")
    @Operation(summary = "Filtrer les machines par état")
    public List<MachineDTO> getByEtat(@PathVariable String etat) {
        return machineService.getByEtat(etat);
    }
}
