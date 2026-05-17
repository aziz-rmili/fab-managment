package com.gestion.fabrication.controller;

import com.gestion.fabrication.dto.EmployeDTO;
import com.gestion.fabrication.service.EmployeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employes")
@CrossOrigin(origins = "*")
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les employés")
    public List<EmployeDTO> getAll() {
        return employeService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un employé par son ID")
    public ResponseEntity<EmployeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel employé")
    public ResponseEntity<EmployeDTO> create(@Valid @RequestBody EmployeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un employé")
    public ResponseEntity<EmployeDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody EmployeDTO dto) {
        return ResponseEntity.ok(employeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un employé")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/machine/{machineId}")
    @Operation(summary = "Lister les employés d'une machine")
    public List<EmployeDTO> getByMachine(@PathVariable Long machineId) {
        return employeService.getByMachine(machineId);
    }
}
