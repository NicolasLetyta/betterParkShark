package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Allocation;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import com.switchfully.apps.betterparkshark.domain.ParkingLot;
import com.switchfully.apps.betterparkshark.repository.AllocationRepository;
import com.switchfully.apps.betterparkshark.repository.MembershipLevelRepository;
import com.switchfully.apps.betterparkshark.repository.ParkingLotRepository;
import com.switchfully.apps.betterparkshark.service.mapper.AllocationMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoOutput;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class AllocationService {

    private AllocationRepository allocationRepository;
    private AllocationMapper allocationMapper;
    private MembershipLevelRepository membershipLevelRepository;
    private ParkingLotRepository parkingLotRepository;

    public AllocationService(AllocationRepository allocationRepository, AllocationMapper allocationMapper,
                              MembershipLevelRepository membershipLevelRepository,
                             ParkingLotRepository parkingLotRepository) {
        this.allocationRepository = allocationRepository;
        this.allocationMapper = allocationMapper;
        this.membershipLevelRepository = membershipLevelRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    public AllocationDtoOutput createNewAllocation(AllocationDtoInput allocationDtoInput, Member member) {
        // check id member, check license plate (different if gold member), parkingid in db,
        // parking lot still places, return allocation id,

        // check if member asking is same as one connected:
        if (allocationDtoInput.getMemberId() != member.getId()) {
            throw new IllegalArgumentException("Member id mismatch, member id for allocation should be the same as the one connected");
        }
        // check license plate (if different from the one from member, need to be gold member
        MembershipLevel membershipLevel = membershipLevelRepository.findById(member.getMembershipLevel());
        if (!Objects.equals(membershipLevel.getName(), "gold") && !Objects.equals(member.getLicensePlate(), allocationDtoInput.getLicensePlate())) {
            throw new IllegalArgumentException("Not a gold member, license plate must be the same as the member license plate");
        }
        // check if parking exist
        ParkingLot parkingLot = parkingLotRepository.findParkingLotById(allocationDtoInput.getParkingId());
        if (parkingLot == null) {
            throw new IllegalArgumentException("No parking lot with this id exists");
        }
        // check if enough place in parking lot
        if(parkingLot.getSpaceAvailable() == 0){
            throw new IllegalArgumentException("No space available in parking lot with this id");
        }
        // remove a spot available for that parking lot
        parkingLot.setSpaceAvailable(parkingLot.getSpaceAvailable() - 1);
        parkingLotRepository.save(parkingLot);

        //Allocation creation
        Allocation allocation = allocationMapper.inputToAllocation(allocationDtoInput);
        allocation = allocationRepository.save(allocation);

        return allocationMapper.allocationToOutput(allocation);
    }

    public AllocationDtoOutput stopAllocation(long id, Member member) {
        // check id member same as in allocation, check if active, end time set to now
        if(allocationRepository.findById(id) == null) {
            throw new IllegalArgumentException("No allocation with this id exists");
        }
        Allocation allocation = allocationRepository.findById(id);

        if(allocation.getMemberId() != member.getId()) {
            throw new IllegalArgumentException("You can not stop this allocation if you are not the member");
        }

        if(allocation.getEndTime() != null) {
            throw new IllegalArgumentException("You can not stop this allocation, it has already been stopped");
        }
        // set end time as now
        allocation.setEndTime(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
        // update database, both with end time and add new available spot in parking lot
        ParkingLot parkingLot = parkingLotRepository.findParkingLotById(allocation.getParkingId());
        parkingLot.setSpaceAvailable(parkingLot.getSpaceAvailable() + 1);
        parkingLotRepository.save(parkingLot);
        allocation = allocationRepository.save(allocation);

        return allocationMapper.allocationToOutput(allocation);
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
