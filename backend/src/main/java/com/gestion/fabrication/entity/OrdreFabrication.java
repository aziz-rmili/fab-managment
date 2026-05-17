package com.gestion.fabrication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ordres_fabrication")
public class OrdreFabrication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le projet est obligatoire")
    @Size(min = 2, max = 150)
    @Column(nullable = false, length = 150)
    private String projet;

    @NotNull(message = "Le produit est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @Min(value = 1, message = "La quantité doit être au moins 1")
    @Column(nullable = false)
    private int quantite;

    @NotNull(message = "La date est obligatoire")
    @Column(nullable = false)
    private LocalDate date;

    @NotBlank(message = "L'état est obligatoire")
    @Column(nullable = false, length = 20)
    private String etat;
}
