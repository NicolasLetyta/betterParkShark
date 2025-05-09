package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Allocation;
import com.switchfully.apps.betterparkshark.repository.AllocationRepository;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import com.switchfully.apps.betterparkshark.service.mapper.AllocationMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoOutput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllocationService {

    private AllocationRepository allocationRepository;
    private AllocationMapper allocationMapper;
    private MemberRepository memberRepository;

    public AllocationService(AllocationRepository allocationRepository, AllocationMapper allocationMapper, MemberRepository memberRepository) {
        this.allocationRepository = allocationRepository;
        this.allocationMapper = allocationMapper;
        this.memberRepository = memberRepository;
    }

    public AllocationDtoInput createNewAllocation(AllocationDtoInput allocationDtoInput) {
        // check id member, id
        return null;
    }

    public AllocationDtoOutput stopAllocation() {
        return null;
    }

    public List<AllocationDtoOutput> getAllAllocations() {
        List<Allocation> allocationList = allocationRepository.findAll();
        return allocationList.stream()
                .map(allocation -> allocationMapper.allocationToOutput(allocation))
                .toList();
    }

    public List<AllocationDtoOutput> getAllAllocations(
            int limit,
            String statusFilter,
            String orderDirection
    ) {
        List<Allocation> allocationList = allocationRepository.findFilteredAllocations(
                limit, statusFilter, orderDirection
        );
        return allocationList.stream()
                .map(allocation -> allocationMapper.allocationToOutput(allocation))
                .toList();
    }

    public List<AllocationDtoOutput> getAllocationsByMemberId(long memberId,String status) {
        List<Allocation> allocations;
        switch (status.toUpperCase()) {
            case "ACTIVE" -> allocations = allocationRepository.findByMemberIdAndEndTimeIsNull(memberId);
            case "STOPPED" -> allocations = allocationRepository.findByMemberIdAndEndTimeIsNotNull(memberId);
            case "ALL" -> allocations = allocationRepository.findByMemberId(memberId);
            default -> throw new IllegalArgumentException("Invalid status: " + status);

        }
        return allocations.stream()
                .map(allocationMapper::allocationToOutput)
                .toList();
    }

    public List<AllocationDtoOutput> getAllocationsByParkingId(long parkingId,String status) {
        List<Allocation> allocations;
        switch (status.toUpperCase()) {
            case "ACTIVE" -> allocations = allocationRepository.findByParkingIdAndEndTimeIsNull(parkingId);
            case "STOPPED" -> allocations = allocationRepository.findByParkingIdAndEndTimeIsNotNull(parkingId);
            case "ALL" -> allocations = allocationRepository.findByParkingId(parkingId);
            default -> throw new IllegalArgumentException("Invalid status: " + status);

        }
        return allocations.stream()
                .map(allocationMapper::allocationToOutput)
                .toList();
    }

}
