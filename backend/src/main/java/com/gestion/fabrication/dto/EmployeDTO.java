package com.gestion.fabrication.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Employe.
 *
 * Pour la machine assignée on expose seulement son ID (machineId)
 * et son nom (machineNom) pour l'affichage — pas l'objet Machine complet.
 * C'est exactement ce que le prof montre slide 21 : le DTO stocke idG
 * au lieu de l'objet Groupe entier.
 */
@Data
@NoArgsConstructor
public class EmployeDTO {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100)
    private String nom;

    @NotBlank(message = "Le poste est obligatoire")
    private String poste;

    // Identifiant de la machine (pour la création/modification)
    private Long machineId;

    // Nom de la machine (pour l'affichage, en lecture seule)
    private String machineNom;
}
