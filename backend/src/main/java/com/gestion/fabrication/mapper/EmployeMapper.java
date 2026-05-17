package com.gestion.fabrication.mapper;

import com.gestion.fabrication.dto.EmployeDTO;
import com.gestion.fabrication.entity.Employe;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper Employe.
 *
 * ModelMapper mappe automatiquement : id, nom, poste.
 *
 * Les champs machineId et machineNom doivent être renseignés
 * manuellement (slide 23 : "Mapping des attributs ajoutés dans le DTO")
 * car ModelMapper ne sait pas extraire machineAssignee.id → machineId.
 */
@Component
public class EmployeMapper {

    private final ModelMapper modelMapper;

    public EmployeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EmployeDTO toDto(Employe employe) {
        EmployeDTO dto = modelMapper.map(employe, EmployeDTO.class);
        // Mapping manuel des attributs ajoutés (slide 23)
        if (employe.getMachineAssignee() != null) {
            dto.setMachineId(employe.getMachineAssignee().getId());
            dto.setMachineNom(employe.getMachineAssignee().getNom());
        }
        return dto;
    }

    public Employe fromDto(EmployeDTO dto) {
        // On crée l'entité sans la machine (la machine sera injectée dans le service)
        Employe employe = new Employe();
        employe.setId(dto.getId());
        employe.setNom(dto.getNom());
        employe.setPoste(dto.getPoste());
        return employe;
    }

    public List<EmployeDTO> toDtoList(List<Employe> employes) {
        return employes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
