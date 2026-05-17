package com.gestion.fabrication.service;

import com.gestion.fabrication.dto.EmployeDTO;
import com.gestion.fabrication.entity.Employe;
import com.gestion.fabrication.entity.Machine;
import com.gestion.fabrication.exception.ResourceNotFoundException;
import com.gestion.fabrication.mapper.EmployeMapper;
import com.gestion.fabrication.repository.EmployeRepository;
import com.gestion.fabrication.repository.MachineRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final MachineRepository machineRepository;
    private final EmployeMapper employeMapper;

    public EmployeService(EmployeRepository employeRepository,
                          MachineRepository machineRepository,
                          EmployeMapper employeMapper) {
        this.employeRepository = employeRepository;
        this.machineRepository = machineRepository;
        this.employeMapper = employeMapper;
    }

    public List<EmployeDTO> getAll() {
        return employeMapper.toDtoList(employeRepository.findAll());
    }

    public EmployeDTO getById(Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé non trouvé avec l'id : " + id));
        return employeMapper.toDto(employe);
    }

    public EmployeDTO create(EmployeDTO dto) {
        Employe employe = employeMapper.fromDto(dto);
        // Injection de la machine si machineId est fourni
        if (dto.getMachineId() != null) {
            Machine machine = machineRepository.findById(dto.getMachineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Machine non trouvée avec l'id : " + dto.getMachineId()));
            employe.setMachineAssignee(machine);
        }
        return employeMapper.toDto(employeRepository.save(employe));
    }

    public EmployeDTO update(Long id, EmployeDTO dto) {
        Employe existing = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé non trouvé avec l'id : " + id));
        existing.setNom(dto.getNom());
        existing.setPoste(dto.getPoste());
        if (dto.getMachineId() != null) {
            Machine machine = machineRepository.findById(dto.getMachineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Machine non trouvée avec l'id : " + dto.getMachineId()));
            existing.setMachineAssignee(machine);
        } else {
            existing.setMachineAssignee(null);
        }
        return employeMapper.toDto(employeRepository.save(existing));
    }

    public void delete(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employé non trouvé avec l'id : " + id);
        }
        employeRepository.deleteById(id);
    }

    public List<EmployeDTO> getByMachine(Long machineId) {
        return employeMapper.toDtoList(employeRepository.findByMachineAssigneeId(machineId));
    }
}
