package com.gestion.fabrication.mapper;

import com.gestion.fabrication.dto.OrdreFabricationDTO;
import com.gestion.fabrication.entity.OrdreFabrication;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper OrdreFabrication.
 *
 * Mapping entièrement manuel ici car les champs du DTO
 * (produitId, machineId, employeId) n'ont pas les mêmes noms
 * que dans l'entité (produit.id, machine.id, employe.id).
 *
 * C'est le cas typique du slide 23 du cours :
 * "Mapping des attributs ajoutés dans le DTO"
 */
@Component
public class OrdreFabricationMapper {

    /** Entité → DTO */
    public OrdreFabricationDTO toDto(OrdreFabrication ordre) {
        OrdreFabricationDTO dto = new OrdreFabricationDTO();
        dto.setId(ordre.getId());
        dto.setProjet(ordre.getProjet());
        dto.setQuantite(ordre.getQuantite());
        dto.setDate(ordre.getDate());
        dto.setEtat(ordre.getEtat());

        if (ordre.getProduit() != null) {
            dto.setProduitId(ordre.getProduit().getId());
            dto.setProduitNom(ordre.getProduit().getNom());
        }
        if (ordre.getMachine() != null) {
            dto.setMachineId(ordre.getMachine().getId());
            dto.setMachineNom(ordre.getMachine().getNom());
        }
        if (ordre.getEmploye() != null) {
            dto.setEmployeId(ordre.getEmploye().getId());
            dto.setEmployeNom(ordre.getEmploye().getNom());
        }
        return dto;
    }

    /** DTO → Entité (les relations seront injectées dans le service) */
    public OrdreFabrication fromDto(OrdreFabricationDTO dto) {
        OrdreFabrication ordre = new OrdreFabrication();
        ordre.setId(dto.getId());
        ordre.setProjet(dto.getProjet());
        ordre.setQuantite(dto.getQuantite());
        ordre.setDate(dto.getDate());
        ordre.setEtat(dto.getEtat());
        return ordre;
    }

    /** Liste → Liste de DTOs (slide 24) */
    public List<OrdreFabricationDTO> toDtoList(List<OrdreFabrication> ordres) {
        return ordres.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
