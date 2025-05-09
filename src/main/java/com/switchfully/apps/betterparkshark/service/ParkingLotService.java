package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.repository.AddressRepository;
import com.switchfully.apps.betterparkshark.repository.DivisionRepository;
import com.switchfully.apps.betterparkshark.repository.EmployeeRepository;
import com.switchfully.apps.betterparkshark.repository.ParkingLotRepository;
import com.switchfully.apps.betterparkshark.service.mapper.AddressMapper;
import com.switchfully.apps.betterparkshark.service.mapper.ParkingLotMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoInput;
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
    private AddressMapper addressMapper;
    private DivisionRepository divisionRepository;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingLotMapper parkingLotMapper, EmployeeRepository employeeRepository, AddressRepository addressRepository, AddressMapper addressMapper, DivisionRepository divisionRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotMapper = parkingLotMapper;
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.divisionRepository = divisionRepository;
    }

    public ParkingLotDtoOutputList createNewParkingLot(ParkingLotDtoInput parkingLotDtoInput) {
        // save address to get an idea for it
        Address address = addressRepository.save(addressMapper.inputToAddress(parkingLotDtoInput.getAddress()));
        //create and save new parkinglot
        ParkingLot newParkingLot = parkingLotMapper.inputToParkingLot(parkingLotDtoInput, address.getId());
        parkingLotRepository.save(newParkingLot);
        //retrieve info for full output
        Employee contactPerson = employeeRepository.findEmployeeById(newParkingLot.getContactPersonId());
        Division division = divisionRepository.findById(newParkingLot.getDivisionId());
        return parkingLotMapper.parkingLotToOutputList(newParkingLot, contactPerson, address, division);
    }


    public List<ParkingLotDtoOutput> findAllParkingLots() {
        List<ParkingLotMinInfo> parkingLots = parkingLotRepository.findAllProjected();

        return parkingLots.stream()
                .map(parkingLotMin -> {
                    Employee contact_person = employeeRepository.findEmployeeById(parkingLotMin.getContactPersonId());
                    return parkingLotMapper.parkingLotToOutput(parkingLotMin, contact_person);
                        })
                .collect(Collectors.toList());

    }

    public ParkingLotDtoOutputList findParkingLotById(Long id) {
        ParkingLot parkingLot = parkingLotRepository.findParkingLotById(id);
        Employee contactPerson = employeeRepository.findEmployeeById(parkingLot.getContactPersonId());
        Address address = addressRepository.findById(parkingLot.getAddressId());
        Division division = divisionRepository.findById(parkingLot.getDivisionId());
        return parkingLotMapper.parkingLotToOutputList(parkingLot, contactPerson, address, division);

    }
}
