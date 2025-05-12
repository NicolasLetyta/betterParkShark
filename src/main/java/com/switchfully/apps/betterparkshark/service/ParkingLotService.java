package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.exception.InvalidInputException;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.switchfully.apps.betterparkshark.utility.Validation.validateArgument;
import static com.switchfully.apps.betterparkshark.utility.Validation.validateNonBlank;

@Service
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapper parkingLotMapper;
    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final DivisionRepository divisionRepository;

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
        // check input
        // check name
        validateNonBlank(parkingLotDtoInput.getName(), "Name cannot be null or blank", InvalidInputException::new);
        if (parkingLotRepository.existsByName(parkingLotDtoInput.getName())) {
            throw new InvalidInputException("A parking lot with that name already exists");
        }
        // check category
        if (Arrays.stream(LotCategory.values()).noneMatch(cat ->cat.name().equalsIgnoreCase(parkingLotDtoInput.getCategory()))){
            throw new InvalidInputException("Invalid category");
        }
        parkingLotDtoInput.setCategory(parkingLotDtoInput.getCategory().toUpperCase());
        //check division exist
        Division division = checkExistingDivision(parkingLotDtoInput.getDivisionId());
        // check contact person
        Employee contactPerson = checkEmployeeIsContactPerson(parkingLotDtoInput.getContactPersonId());
        // check if no negative number
        checkIfPositveOr0Int(parkingLotDtoInput.getMaxCapacity());
        checkIfPositveOr0Double(parkingLotDtoInput.getPriceHour());

        //create and save new parkinglot
        ParkingLot newParkingLot = parkingLotMapper.inputToParkingLot(parkingLotDtoInput, address.getId());
        parkingLotRepository.save(newParkingLot);

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

    public ParkingLotDtoOutputList findParkingLotById(long id) {
        validateArgument(id,"Parking lot not found in repository", i->!parkingLotRepository.existsById(i),InvalidInputException::new);
        ParkingLot parkingLot = parkingLotRepository.findById(id).get();

        Employee contactPerson = checkExistingEmployee(parkingLot.getContactPersonId());

        validateArgument(parkingLot.getAddressId(),"Address not found in repository", i->!addressRepository.existsById(i),InvalidInputException::new);
        Address address = addressRepository.findById(parkingLot.getAddressId()).get();
        Division division = checkExistingDivision(parkingLot.getDivisionId());
        return parkingLotMapper.parkingLotToOutputList(parkingLot, contactPerson, address, division);

    }

    public Division checkExistingDivision(Long id) {
        validateArgument(id,"Division not found in repository", i->!divisionRepository.existsById(i),InvalidInputException::new);
        return divisionRepository.findById(id).get();
    }

    public Employee checkExistingEmployee(Long id) {
        validateArgument(id, "Employee not found in repository", i->!employeeRepository.existsById(i),InvalidInputException::new);
        return employeeRepository.findById(id).get();
    }

    public Employee checkEmployeeIsContactPerson(Long id) {
        Employee employee = checkExistingEmployee(id);
        if (!Objects.equals(String.valueOf(employee.getTypeEmployee()), "CONTACT_PERSON")){
            throw new InvalidInputException("Employee is not a CONTACT_PERSON");
        }
        return employee;
    }

    public void checkIfPositveOr0Int (int capacity){
        if (capacity < 0){
            throw new InvalidInputException("Capacity cannot be negative");
        }
    }

    public void checkIfPositveOr0Double (double price){
        if (price < 0){
            throw new InvalidInputException("Price cannot be negative");
        }
    }
}
