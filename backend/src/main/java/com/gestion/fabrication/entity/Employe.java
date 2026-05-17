package com.gestion.fabrication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employes")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "Le poste est obligatoire")
    @Column(nullable = false, length = 100)
    private String poste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machineAssignee;

    @OneToMany(mappedBy = "employe")
    private List<OrdreFabrication> ordres = new ArrayList<>();
}
