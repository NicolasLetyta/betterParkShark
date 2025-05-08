package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.repository.AddressRepository;
import com.switchfully.apps.betterparkshark.repository.DivisionRepository;
import com.switchfully.apps.betterparkshark.repository.EmployeeRepository;
import com.switchfully.apps.betterparkshark.repository.ParkingLotRepository;
import com.switchfully.apps.betterparkshark.service.mapper.ParkingLotMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoOutputList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {

    private ParkingLotRepository parkingLotRepository;
    private ParkingLotMapper parkingLotMapper;
    private EmployeeRepository employeeRepository;
    private AddressRepository addressRepository;
    private DivisionRepository divisionRepository;


    public List<ParkingLotDtoOutput> findAllParkingLots() {
        List<ParkingLotMinInfo> parkingLots = parkingLotRepository.findAllProjected();
        
        return parkingLots.stream()
                .map(parkingLotMin -> {
                    Employee contact_person = employeeRepository.findEmployeeById(parkingLotMin.getContactPersonId());
                    return parkingLotMapper.parkingLotToOutput2Phone(parkingLotMin, contact_person);
                        })
                .collect(Collectors.toList());

    }

    public ParkingLotDtoOutputList findParkingLotById(Long id) {
        ParkingLot parkingLot = parkingLotRepository.findParkingLotById(id);
        Employee contactPerson = employeeRepository.findEmployeeById(parkingLot.getContactPersonId());
        Address address = addressRepository.findById(parkingLot.getAddressId());
        Division division = divisionRepository.findById(parkingLot.getDivisionId());
        return parkingLotMapper.parkingLotToOutputList2Phone(parkingLot, contactPerson, address, division);

    }
}
