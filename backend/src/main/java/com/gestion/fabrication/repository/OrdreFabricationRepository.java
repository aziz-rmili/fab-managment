package com.gestion.fabrication.repository;

import com.gestion.fabrication.entity.OrdreFabrication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdreFabricationRepository extends JpaRepository<OrdreFabrication, Long> {
    List<OrdreFabrication> findByEtat(String etat);
    List<OrdreFabrication> findByProduitId(Long produitId);
    List<OrdreFabrication> findByEmployeId(Long employeId);
    List<OrdreFabrication> findByMachineId(Long machineId);
}
