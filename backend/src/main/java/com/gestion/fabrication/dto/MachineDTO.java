package com.gestion.fabrication.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MachineDTO {

    private Long id;

    @NotBlank(message = "Le nom de la machine est obligatoire")
    @Size(min = 2, max = 100)
    private String nom;

    @NotBlank(message = "L'état est obligatoire")
    private String etat;

    private LocalDate derniereMaintenance;
}
