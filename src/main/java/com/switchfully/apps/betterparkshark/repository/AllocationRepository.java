package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllocationRepository
        extends JpaRepository<ParkingLot, Long> {


}
