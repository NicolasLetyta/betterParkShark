package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Division;
import com.switchfully.apps.betterparkshark.domain.Employee;
import com.switchfully.apps.betterparkshark.domain.EmployeeCategory;
import com.switchfully.apps.betterparkshark.exception.InvalidInputException;
import com.switchfully.apps.betterparkshark.repository.DivisionRepository;
import com.switchfully.apps.betterparkshark.repository.EmployeeRepository;
import com.switchfully.apps.betterparkshark.service.mapper.DivisionMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.SubDivisionDtoInput;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DivisionService {

    // FIELDS
    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;
    private final EmployeeRepository employeeRepository;

    // CONSTRUCTORS
    public DivisionService(DivisionRepository divisionRepository, DivisionMapper divisionMapper, EmployeeRepository employeeRepository) {
        this.divisionRepository = divisionRepository;
        this.divisionMapper = divisionMapper;
        this.employeeRepository = employeeRepository;
    }

    // METHODS
    public DivisionDtoOutput createDivision(DivisionDtoInput divisionDtoInput) {
        checkDivisionNameValidity(divisionDtoInput.getName(), divisionDtoInput.getOriginalName());
        checkDivisionDirectorValidity(divisionDtoInput.getDirectorId());

        Division division = divisionMapper.InputToDivision(divisionDtoInput);
        return divisionMapper.DivisionToOutput(divisionRepository.save(division));
    }

    public DivisionDtoOutput createSubDivision(SubDivisionDtoInput subDivisionDtoInput) {
        checkDivisionNameValidity(subDivisionDtoInput.getName(), subDivisionDtoInput.getOriginalName());
        checkDivisionDirectorValidity(subDivisionDtoInput.getDirectorId());
        checkSubDivisionParentValidity(subDivisionDtoInput.getParentId());

        Division subDivision = divisionMapper.InputSubToDivision(subDivisionDtoInput);
        return divisionMapper.DivisionToOutput(divisionRepository.save(subDivision));
    }

    public DivisionDtoOutput getDivision(Long id) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new InvalidInputException("Division not found"));
        return divisionMapper.DivisionToOutput(division);
    }

    public List<DivisionDtoOutput> getAllDivisions() {
        List<Division> divisions = divisionRepository.findAll();
        return divisions.stream()
                .map(divisionMapper::DivisionToOutput)
                .toList();
    }

    // Validation
    void checkDivisionNameValidity(String name, String originalName) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Division name cannot be blank");
        }
        if (originalName == null || originalName.isBlank()) {
            throw new InvalidInputException("Division original name cannot be blank");
        }
        if (divisionRepository.existsByName(name)) {
            throw new InvalidInputException("Division name already in use");
        }
    }

    void checkDivisionDirectorValidity(Long directorId) {
        if (directorId == null) {
            throw new InvalidInputException("Division director ID cannot be null");
        }
        Employee director = employeeRepository.findById(directorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Division director not found"));
        if (!director.getTypeEmployee().equals(EmployeeCategory.DIRECTOR)) {
            throw new InvalidInputException("Division director must be a director");
        }
    }

    void checkSubDivisionParentValidity(Long parentId) {
        if (parentId == null) {
            throw new InvalidInputException("Sub-division parent ID cannot be null");
        }
        divisionRepository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sub-division parent not found"));
    }
}
