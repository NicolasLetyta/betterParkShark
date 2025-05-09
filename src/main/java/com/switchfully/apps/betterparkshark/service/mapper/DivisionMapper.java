package com.switchfully.apps.betterparkshark.service.mapper;

import com.switchfully.apps.betterparkshark.domain.Division;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.SubDivisionDtoInput;
import org.springframework.stereotype.Component;

@Component
public class DivisionMapper {

    // METHODS
    public Division InputToDivision(DivisionDtoInput divisionDtoInput) {
        return new Division(
                divisionDtoInput.getName(),
                divisionDtoInput.getOriginalName(),
                divisionDtoInput.getDirectorId(),
                null
        );
    }

    public DivisionDtoOutput DivisionToOutput(Division division) {
        return new DivisionDtoOutput(
                division.getId(),
                division.getName(),
                division.getOriginalName(),
                division.getDirectorId(),
                division.getParentId()
        );
    }

    public Division InputSubToDivision(SubDivisionDtoInput subDivisionDtoInput) {
        return new Division(
                subDivisionDtoInput.getName(),
                subDivisionDtoInput.getOriginalName(),
                subDivisionDtoInput.getDirectorId(),
                subDivisionDtoInput.getParentId()
        );
    }
}


