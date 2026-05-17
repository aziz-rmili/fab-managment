package com.gestion.fabrication.service;

import com.gestion.fabrication.dto.ProduitDTO;
import com.gestion.fabrication.entity.Produit;
import com.gestion.fabrication.exception.ResourceNotFoundException;
import com.gestion.fabrication.mapper.ProduitMapper;
import com.gestion.fabrication.repository.ProduitRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service Produit — couche métier.
 *
 * Cours du prof slides 12-13 :
 * "La couche service est dédiée au métier."
 * "Appliquer des traitements dictés par les règles fonctionnelles."
 *
 * C'est ici qu'on inject le repository ET le mapper (slide 25),
 * pas dans le contrôleur.
 */
@Service
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    // Injection par constructeur (bonne pratique Spring)
    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    public List<ProduitDTO> getAll() {
        return produitMapper.toDtoList(produitRepository.findAll());
    }

    public ProduitDTO getById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id : " + id));
        return produitMapper.toDto(produit);
    }

    public ProduitDTO create(ProduitDTO dto) {
        Produit produit = produitMapper.fromDto(dto);
        return produitMapper.toDto(produitRepository.save(produit));
    }

    public ProduitDTO update(Long id, ProduitDTO dto) {
        Produit existing = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id : " + id));
        existing.setNom(dto.getNom());
        existing.setType(dto.getType());
        existing.setStock(dto.getStock());
        existing.setFournisseur(dto.getFournisseur());
        return produitMapper.toDto(produitRepository.save(existing));
    }

    public void delete(Long id) {
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit non trouvé avec l'id : " + id);
        }
        produitRepository.deleteById(id);
    }

    public List<ProduitDTO> getByType(String type) {
        return produitMapper.toDtoList(produitRepository.findByType(type));
    }

    public List<ProduitDTO> getStockFaible(int seuil) {
        return produitMapper.toDtoList(produitRepository.findByStockLessThan(seuil));
    }
}
