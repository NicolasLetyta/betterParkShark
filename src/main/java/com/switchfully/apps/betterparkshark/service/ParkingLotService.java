package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Employee;
import com.switchfully.apps.betterparkshark.domain.ParkingLot;
import com.switchfully.apps.betterparkshark.repository.DivisionRepository;
import com.switchfully.apps.betterparkshark.repository.EmployeeRepository;
import com.switchfully.apps.betterparkshark.repository.ParkingLotRepository;
import com.switchfully.apps.betterparkshark.service.mapper.ParkingLotMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoOutput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapper parkingLotMapper;
    private EmployeeRepository employeeRepository;
    // private AddressRepository addressRepository;
    private DivisionRepository divisionRepository;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingLotMapper parkingLotMapper) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotMapper = parkingLotMapper;
    }

    public List<ParkingLotDtoOutput> findAllParkingLots() {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        //parkingLots.forEach(parkingLot -> {})
        //Employee contactPerson =  employeeRepository.findEmployeeById(long id)
        return null;
    }
}
