package com.gestion.fabrication.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Produit — ce que l'API reçoit et renvoie.
 *
 * Principe cours prof (slides 19-24) :
 * On N'utilise PAS les entités directement dans l'API.
 * Le DTO ne contient que des champs plats (pas d'objets entité)
 * → évite les références circulaires JSON sans @JsonIgnore.
 */
@Data
@NoArgsConstructor
public class ProduitDTO {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit avoir entre 2 et 100 caractères")
    private String nom;

    @NotBlank(message = "Le type est obligatoire")
    private String type;

    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private int stock;

    @NotBlank(message = "Le fournisseur est obligatoire")
    private String fournisseur;
}
