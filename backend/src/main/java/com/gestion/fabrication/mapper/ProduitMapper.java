package com.gestion.fabrication.mapper;

import com.gestion.fabrication.dto.ProduitDTO;
import com.gestion.fabrication.entity.Produit;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper Produit — converti entre entité et DTO.
 *
 * Cours du prof slides 22-24 :
 *  toDto()     : de l'entité vers le DTO (pour renvoyer à l'API)
 *  fromDto()   : du DTO vers l'entité  (pour sauvegarder en base)
 *  toDtoList() : convertit une liste d'entités en liste de DTOs
 */
@Component
public class ProduitMapper {

    private final ModelMapper modelMapper;

    public ProduitMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /** Entité → DTO (slide 23 : mapping automatique car mêmes noms) */
    public ProduitDTO toDto(Produit produit) {
        return modelMapper.map(produit, ProduitDTO.class);
    }

    /** DTO → Entité (slide 23 : mapping automatique) */
    public Produit fromDto(ProduitDTO dto) {
        return modelMapper.map(dto, Produit.class);
    }

    /** Liste entités → Liste DTOs (slide 24) */
    public List<ProduitDTO> toDtoList(List<Produit> produits) {
        return produits.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
