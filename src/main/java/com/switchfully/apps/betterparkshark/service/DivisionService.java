package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Division;
import com.switchfully.apps.betterparkshark.repository.DivisionRepository;
import com.switchfully.apps.betterparkshark.service.mapper.DivisionMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.SubDivisionDtoInput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DivisionService {

    // FIELDS
    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;

    // CONSTRUCTORS
    public DivisionService(DivisionRepository divisionRepository, DivisionMapper divisionMapper) {
        this.divisionRepository = divisionRepository;
        this.divisionMapper = divisionMapper;
    }

    // METHODS
    public DivisionDtoOutput createDivision(DivisionDtoInput divisionDtoInput) {
        Division division = divisionMapper.InputToDivision(divisionDtoInput);
        return divisionMapper.DivisionToOutput(divisionRepository.save(division));
    }

    public DivisionDtoOutput createSubDivision(SubDivisionDtoInput subDivisionDtoInput) {
        Division subDivision = divisionMapper.InputSubToDivision(subDivisionDtoInput);
        return divisionMapper.DivisionToOutput(divisionRepository.save(subDivision));
    }

    public DivisionDtoOutput getDivision(Long id) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Division not found"));
        return divisionMapper.DivisionToOutput(division);
    }

    public List<DivisionDtoOutput> getAllDivisions() {
        List<Division> divisions = divisionRepository.findAll();
        return divisions.stream()
                .map(divisionMapper::DivisionToOutput)
                .toList();
    }

}
