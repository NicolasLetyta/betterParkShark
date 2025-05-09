package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.service.AllocationService;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allocations")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<AllocationDtoOutput> getFilteredAllocations(
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "ALL") String status,
            @RequestParam(defaultValue = "ASC") String order
    ) {
        return allocationService.getAllAllocations(limit, status, order);
    }

    @GetMapping("/member/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AllocationDtoOutput> getAllocationsByMember(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "ALL") String status
    ) {
        return allocationService.getAllocationsByMemberId(memberId, status);
    }

    @GetMapping("/parkinglot/{parkingLotId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AllocationDtoOutput> getAllocationsByParkingLot(
            @PathVariable Long parkingLotId,
            @RequestParam(defaultValue = "ALL") String status
    ) {
        return allocationService.getAllocationsByParkingId(parkingLotId, status);
    }
}
