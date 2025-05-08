package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.repository.DivisionRepository;
import com.switchfully.apps.betterparkshark.service.mapper.DivisionMapper;
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
        return null;
    }

    public DivisionDtoOutput createSubDivision(SubDivisionDtoInput subDivisionDtoInput) {
        return null;
    }

    public DivisionDtoOutput getDivision(Long id) {
        return null;
    }

    public List<DivisionDtoOutput> getAllDivisions() {
        return null;
    }

}
