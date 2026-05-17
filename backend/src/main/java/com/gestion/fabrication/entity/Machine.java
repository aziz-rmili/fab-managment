package com.gestion.fabrication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la machine est obligatoire")
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "L'état est obligatoire")
    @Column(nullable = false, length = 30)
    private String etat;

    @Column(name = "derniere_maintenance")
    private LocalDate derniereMaintenance;

    @OneToMany(mappedBy = "machineAssignee")
    private List<Employe> employes = new ArrayList<>();

    @OneToMany(mappedBy = "machine")
    private List<OrdreFabrication> ordres = new ArrayList<>();
}
