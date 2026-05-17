package com.gestion.fabrication.service;

import com.gestion.fabrication.dto.MachineDTO;
import com.gestion.fabrication.entity.Machine;
import com.gestion.fabrication.exception.ResourceNotFoundException;
import com.gestion.fabrication.mapper.MachineMapper;
import com.gestion.fabrication.repository.MachineRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper machineMapper;

    public MachineService(MachineRepository machineRepository, MachineMapper machineMapper) {
        this.machineRepository = machineRepository;
        this.machineMapper = machineMapper;
    }

    public List<MachineDTO> getAll() {
        return machineMapper.toDtoList(machineRepository.findAll());
    }

    public MachineDTO getById(Long id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine non trouvée avec l'id : " + id));
        return machineMapper.toDto(machine);
    }

    public MachineDTO create(MachineDTO dto) {
        Machine machine = machineMapper.fromDto(dto);
        return machineMapper.toDto(machineRepository.save(machine));
    }

    public MachineDTO update(Long id, MachineDTO dto) {
        Machine existing = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine non trouvée avec l'id : " + id));
        existing.setNom(dto.getNom());
        existing.setEtat(dto.getEtat());
        existing.setDerniereMaintenance(dto.getDerniereMaintenance());
        return machineMapper.toDto(machineRepository.save(existing));
    }

    public void delete(Long id) {
        if (!machineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Machine non trouvée avec l'id : " + id);
        }
        machineRepository.deleteById(id);
    }

    public List<MachineDTO> getByEtat(String etat) {
        return machineMapper.toDtoList(machineRepository.findByEtat(etat));
    }
}
