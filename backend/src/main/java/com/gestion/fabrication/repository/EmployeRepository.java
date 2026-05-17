package com.gestion.fabrication.repository;

import com.gestion.fabrication.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByMachineAssigneeId(Long machineId);
    List<Employe> findByPoste(String poste);
}
