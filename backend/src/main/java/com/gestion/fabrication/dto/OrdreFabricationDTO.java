package com.gestion.fabrication.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * DTO OrdreFabrication.
 *
 * Au lieu d'inclure les objets Produit/Machine/Employe complets
 * (ce qui crée des boucles infinies), on stocke uniquement :
 *  - l'ID pour identifier l'entité (utilisé en écriture)
 *  - le nom pour l'affichage (calculé lors de la conversion)
 *
 * C'est exactement ce que le prof montre slide 21 :
 * "Définir le DTO en évitant l'utilisation des entités en tant qu'attribut."
 */
@Data
@NoArgsConstructor
public class OrdreFabricationDTO {

    private Long id;

    @NotBlank(message = "Le projet est obligatoire")
    @Size(min = 2, max = 150)
    private String projet;

    // === Produit (obligatoire) ===
    @NotNull(message = "L'ID du produit est obligatoire")
    private Long produitId;
    private String produitNom; // affichage uniquement

    // === Machine (optionnelle) ===
    private Long machineId;
    private String machineNom; // affichage uniquement

    // === Employé (optionnel) ===
    private Long employeId;
    private String employeNom; // affichage uniquement

    @Min(value = 1, message = "La quantité doit être au moins 1")
    private int quantite;

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @NotBlank(message = "L'état est obligatoire")
    private String etat;
}
