package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.ParkingLot;
import com.switchfully.apps.betterparkshark.domain.ParkingLotMinInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository
        extends JpaRepository<ParkingLot, Long> {

    @Query("select p.id, p.name, p.maxCapacity, p.contactPersonId from ParkingLot p")
    List<ParkingLotMinInfo> findAllProjected();

    ParkingLot findParkingLotById(long id);


}
