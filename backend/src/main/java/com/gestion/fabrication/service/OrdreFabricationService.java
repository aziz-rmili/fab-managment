package com.gestion.fabrication.service;

import com.gestion.fabrication.dto.OrdreFabricationDTO;
import com.gestion.fabrication.entity.*;
import com.gestion.fabrication.exception.ResourceNotFoundException;
import com.gestion.fabrication.mapper.OrdreFabricationMapper;
import com.gestion.fabrication.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service OrdreFabrication — couche métier.
 *
 * C'est ici que se passe toute la logique :
 * - Récupérer les entités liées depuis leurs repositories
 * - Les injecter dans l'entité avant de sauvegarder
 * - Convertir avec le mapper avant de retourner au contrôleur
 *
 * Voir slide 25 du cours :
 * "Injection du mapper déjà défini dans la classe service"
 * "Recherche de l'entité et sa conversion en DTO"
 */
@Service
public class OrdreFabricationService {

    private final OrdreFabricationRepository ordreRepository;
    private final ProduitRepository produitRepository;
    private final MachineRepository machineRepository;
    private final EmployeRepository employeRepository;
    private final OrdreFabricationMapper ordreMapper;

    public OrdreFabricationService(OrdreFabricationRepository ordreRepository,
                                   ProduitRepository produitRepository,
                                   MachineRepository machineRepository,
                                   EmployeRepository employeRepository,
                                   OrdreFabricationMapper ordreMapper) {
        this.ordreRepository = ordreRepository;
        this.produitRepository = produitRepository;
        this.machineRepository = machineRepository;
        this.employeRepository = employeRepository;
        this.ordreMapper = ordreMapper;
    }

    public List<OrdreFabricationDTO> getAll() {
        return ordreMapper.toDtoList(ordreRepository.findAll());
    }

    public OrdreFabricationDTO getById(Long id) {
        OrdreFabrication ordre = ordreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordre non trouvé avec l'id : " + id));
        return ordreMapper.toDto(ordre);
    }

    public OrdreFabricationDTO create(OrdreFabricationDTO dto) {
        OrdreFabrication ordre = ordreMapper.fromDto(dto);
        injecterRelations(ordre, dto);
        return ordreMapper.toDto(ordreRepository.save(ordre));
    }

    public OrdreFabricationDTO update(Long id, OrdreFabricationDTO dto) {
        OrdreFabrication existing = ordreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordre non trouvé avec l'id : " + id));
        existing.setProjet(dto.getProjet());
        existing.setQuantite(dto.getQuantite());
        existing.setDate(dto.getDate());
        existing.setEtat(dto.getEtat());
        injecterRelations(existing, dto);
        return ordreMapper.toDto(ordreRepository.save(existing));
    }

    public void delete(Long id) {
        if (!ordreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ordre non trouvé avec l'id : " + id);
        }
        ordreRepository.deleteById(id);
    }

    public OrdreFabricationDTO changerEtat(Long id, String nouvelEtat) {
        OrdreFabrication ordre = ordreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordre non trouvé avec l'id : " + id));
        ordre.setEtat(nouvelEtat.trim());
        return ordreMapper.toDto(ordreRepository.save(ordre));
    }

    public List<OrdreFabricationDTO> getByEtat(String etat) {
        return ordreMapper.toDtoList(ordreRepository.findByEtat(etat));
    }

    public List<OrdreFabricationDTO> getByProduit(Long produitId) {
        return ordreMapper.toDtoList(ordreRepository.findByProduitId(produitId));
    }

    /**
     * Méthode privée : injecte les entités liées (Produit, Machine, Employe)
     * dans l'ordre à partir des IDs fournis dans le DTO.
     */
    private void injecterRelations(OrdreFabrication ordre, OrdreFabricationDTO dto) {
        // Produit obligatoire
        Produit produit = produitRepository.findById(dto.getProduitId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id : " + dto.getProduitId()));
        ordre.setProduit(produit);

        // Machine optionnelle
        if (dto.getMachineId() != null) {
            Machine machine = machineRepository.findById(dto.getMachineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Machine non trouvée avec l'id : " + dto.getMachineId()));
            ordre.setMachine(machine);
        } else {
            ordre.setMachine(null);
        }

        // Employé optionnel
        if (dto.getEmployeId() != null) {
            Employe employe = employeRepository.findById(dto.getEmployeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employé non trouvé avec l'id : " + dto.getEmployeId()));
            ordre.setEmploye(employe);
        } else {
            ordre.setEmploye(null);
        }
    }
}
