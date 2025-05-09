package com.switchfully.apps.betterparkshark.service.mapper;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoOutputList;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotMapper {

    private AddressMapper addressMapper;

    public ParkingLotMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public ParkingLot inputToParkingLot(ParkingLotDtoInput parkingLotDtoInput, long addressId) {
        return new ParkingLot(
                parkingLotDtoInput.getName(),
                parkingLotDtoInput.getCategory(),
                parkingLotDtoInput.getMaxCapacity(),
                parkingLotDtoInput.getPriceHour(),
                parkingLotDtoInput.getContactPersonId(),
                addressId,
                parkingLotDtoInput.getDivisionId()
        );
    }

    public ParkingLotDtoOutput parkingLotToOutput(ParkingLotMinInfo parkingLot, Employee contactPerson) {
        return new ParkingLotDtoOutput(
                parkingLot.getId(),
                parkingLot.getName(),
                parkingLot.getMaxCapacity(),
                contactPerson.getMobilePhone(),
                contactPerson.getTelephone(),
                contactPerson.getEmail()
        );
    }

    public ParkingLotDtoOutputList parkingLotToOutputList(ParkingLot parkingLot, Employee contactPerson, Address address, Division division) {
        return new ParkingLotDtoOutputList(
                parkingLot.getId(),
                parkingLot.getName(),
                parkingLot.getLotCategory(),
                parkingLot.getMaxCapacity(),
                parkingLot.getPriceHour(),
                addressMapper.addressToOutput(address),
                contactPerson.getFirstName(),
                contactPerson.getLastName(),
                contactPerson.getTelephone(),
                contactPerson.getMobilePhone(),
                contactPerson.getEmail(),
                division.getId(),
                division.getName()
        );

    }

}
