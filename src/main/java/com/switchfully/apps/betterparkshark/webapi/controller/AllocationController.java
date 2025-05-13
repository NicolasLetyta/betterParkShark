package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import com.switchfully.apps.betterparkshark.service.AllocationService;
import com.switchfully.apps.betterparkshark.service.AuthenticationService;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allocations")
public class AllocationController {
    private static final Logger logger = LoggerFactory.getLogger(AllocationController.class);

    private final AllocationService allocationService;
    private final MemberRepository memberRepository;
    private final AuthenticationService authenticationService;

    public AllocationController(AllocationService allocationService, MemberRepository memberRepository, AuthenticationService authenticationService) {
        this.allocationService = allocationService;
        this.memberRepository = memberRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<AllocationDtoOutput> getFilteredAllocations(
            @RequestParam(defaultValue = "100") int limit,
            @RequestParam(defaultValue = "ALL") String status,
            @RequestParam(defaultValue = "ASC") String order,
            @RequestHeader(value = "Authorization") String authToken
    ) {
        logger.info("Inside getFilteredAllocations");

        authenticationService.authenticateAdmin(authToken);
        return allocationService.getAllAllocations(limit, status, order);
    }

    @GetMapping(path = "/member/{memberId}",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<AllocationDtoOutput> getAllocationsByMember(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "ALL") String status,
            @RequestHeader(value = "Authorization") String authToken
    ) {
        logger.info("Inside getAllocationsByMember");
        authenticationService.authenticateAdmin(authToken);
        return allocationService.getAllocationsByMemberId(memberId, status);
    }

    @GetMapping(path = "/parkinglot/{parkingLotId}",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<AllocationDtoOutput> getAllocationsByParkingLot(
            @PathVariable Long parkingLotId,
            @RequestParam(defaultValue = "ALL") String status,
            @RequestHeader(value = "Authorization") String authToken
    ) {
        logger.info("Inside getAllocationsByParkingLot");
        authenticationService.authenticateAdmin(authToken);
        return allocationService.getAllocationsByParkingId(parkingLotId, status);
    }

    @PostMapping(consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public AllocationDtoOutput createAllocation(@RequestBody AllocationDtoInput allocationDtoInput,
                                                @RequestHeader(value = "Authorization") String authToken
    ) {
        logger.info("Inside createAllocation");
        Member member = authenticationService.authenticateMember(authToken);
        return allocationService.createNewAllocation(allocationDtoInput, member);
    }

    @PostMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public AllocationDtoOutput stopAllocation(@PathVariable Long id,
                                              @RequestHeader(value = "Authorization") String authToken) {
        logger.info("Inside stopAllocation");
        Member member = authenticationService.authenticateMember(authToken);
        return allocationService.stopAllocation(id, member);
    }
}
