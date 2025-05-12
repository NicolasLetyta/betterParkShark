package com.switchfully.apps.betterparkshark.service.mapper;

import com.switchfully.apps.betterparkshark.domain.Allocation;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoOutput;
import org.springframework.stereotype.Component;

@Component
public class AllocationMapper {

    public Allocation inputToAllocation(AllocationDtoInput allocationDtoInput) {
        return new Allocation(
                allocationDtoInput.getMemberId(),
                allocationDtoInput.getLicensePlate(),
                allocationDtoInput.getParkingId()
        );
    }

    public AllocationDtoOutput allocationToOutput(Allocation allocation) {
        return new AllocationDtoOutput(
                allocation.getId(),
                allocation.getMemberId(),
                allocation.getLicensePlate(),
                allocation.getParkingId(),
                allocation.getStartTime(),
                allocation.getEndTime()
        );
    }


}
