package com.gestion.fabrication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité Produit — table "produits".
 *
 * Les relations bidirectionnelles sont gérées côté DTO/Mapper.
 * Pas de @JsonIgnore ni @JsonIgnoreProperties ici, comme recommandé par le prof.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit avoir entre 2 et 100 caractères")
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "Le type est obligatoire")
    @Column(nullable = false, length = 50)
    private String type;

    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    @Column(nullable = false)
    private int stock;

    @NotBlank(message = "Le fournisseur est obligatoire")
    @Column(nullable = false, length = 100)
    private String fournisseur;

    // Relation bidirectionnelle — ignorée dans le DTO pour éviter les boucles
    @OneToMany(mappedBy = "produit")
    private List<OrdreFabrication> ordres = new ArrayList<>();
}
