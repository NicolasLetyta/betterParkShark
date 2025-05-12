package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import com.switchfully.apps.betterparkshark.service.AllocationService;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoOutput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allocations")
public class AllocationController {

    private final AllocationService allocationService;
    private MemberRepository memberRepository;

    public AllocationController(AllocationService allocationService, MemberRepository memberRepository) {
        this.allocationService = allocationService;
        this.memberRepository = memberRepository;
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

    @GetMapping(path = "/member/{memberId}",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<AllocationDtoOutput> getAllocationsByMember(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "ALL") String status
    ) {
        return allocationService.getAllocationsByMemberId(memberId, status);
    }

    @GetMapping(path = "/parkinglot/{parkingLotId}",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<AllocationDtoOutput> getAllocationsByParkingLot(
            @PathVariable Long parkingLotId,
            @RequestParam(defaultValue = "ALL") String status
    ) {
        return allocationService.getAllocationsByParkingId(parkingLotId, status);
    }

    @PostMapping(consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public AllocationDtoOutput createAllocation(@RequestBody AllocationDtoInput allocationDtoInput) {
        Member member = memberRepository.findById(allocationDtoInput.getMemberId()).get();
        return allocationService.createNewAllocation(allocationDtoInput, member);
    }

    @PostMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public AllocationDtoOutput stopAllocation(@PathVariable Long id) {
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();
        return allocationService.stopAllocation(id, member);
    }
}
