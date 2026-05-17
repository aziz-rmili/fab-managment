package com.gestion.fabrication.mapper;

import com.gestion.fabrication.dto.MachineDTO;
import com.gestion.fabrication.entity.Machine;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MachineMapper {

    private final ModelMapper modelMapper;

    public MachineMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MachineDTO toDto(Machine machine) {
        return modelMapper.map(machine, MachineDTO.class);
    }

    public Machine fromDto(MachineDTO dto) {
        return modelMapper.map(dto, Machine.class);
    }

    public List<MachineDTO> toDtoList(List<Machine> machines) {
        return machines.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
