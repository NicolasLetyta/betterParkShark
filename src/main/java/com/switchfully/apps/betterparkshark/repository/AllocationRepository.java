package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllocationRepository
        extends JpaRepository<Allocation, Long>, AllocationRepositoryCustom {

    List<Allocation> findAll();

    Allocation findById(long id);

    // find by member
    List<Allocation> findByMemberId(long memberId);
    // member and status
    List<Allocation> findByMemberIdAndEndTimeIsNull(Long memberId);
    List<Allocation> findByMemberIdAndEndTimeIsNotNull(Long memberId);

    // find by parking lot
    List<Allocation> findByParkingId(long parkingLotId);
    // parking lot and status
    List<Allocation> findByParkingIdAndEndTimeIsNull(Long parkingLotId);
    List<Allocation> findByParkingIdAndEndTimeIsNotNull(Long parkingLotId);

}
