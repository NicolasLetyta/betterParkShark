package com.switchfully.apps.betterparkshark.service.mapper;

import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Division;
import com.switchfully.apps.betterparkshark.domain.Employee;
import com.switchfully.apps.betterparkshark.domain.ParkingLot;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoOutputList;

public class ParkingLotMapper {

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

    public ParkingLotDtoOutput parkingLotToOutput2Phone(ParkingLot parkingLot, Employee contactPerson) {
        long mobilePhone =25;
        long phone = 26;
        String email = "test@test.com";
        return new ParkingLotDtoOutput(
                parkingLot.getId(),
                parkingLot.getName(),
                parkingLot.getMaxCapacity(),
                mobilePhone,
                phone,
                email
        );
    }

    public ParkingLotDtoOutput parkingLotToOutput1Phone(ParkingLot parkingLot, Employee contactPerson) {
        long phone = 26;
        String email = "test@test.com";
        return new ParkingLotDtoOutput(
                parkingLot.getId(),
                parkingLot.getName(),
                parkingLot.getMaxCapacity(),
                phone,
                email
        );
    }

    public ParkingLotDtoOutputList parkingLotToOutputList1Phone(ParkingLot parkingLot, Employee contactPerson, Address address, Division division) {
        long mobilePhone =25;
        String email = "test@test.com";
        String firstName = "test";
        String lastName = "test";
        long divisionId = 1;
        String divisionName = "test";

        AddressDtoOutput addressDtoOutput = new AddressDtoOutput();

        return new ParkingLotDtoOutputList(
                parkingLot.getId(),
                parkingLot.getName(),
                parkingLot.getLotCategory(),
                parkingLot.getMaxCapacity(),
                parkingLot.getPriceHour(),
                addressDtoOutput,
                firstName,
                lastName,
                mobilePhone,
                email,
                divisionId,
                divisionName
        );

    }

    public ParkingLotDtoOutputList parkingLotToOutputList2Phone(ParkingLot parkingLot, Employee contactPerson, Address address, Division division) {
        long mobilePhone =25;
        long phone = 26;
        String email = "test@test.com";
        String firstName = "test";
        String lastName = "test";
        long divisionId = 1;
        String divisionName = "test";

        AddressDtoOutput addressDtoOutput = new AddressDtoOutput();

        return new ParkingLotDtoOutputList(
                parkingLot.getId(),
                parkingLot.getName(),
                parkingLot.getLotCategory(),
                parkingLot.getMaxCapacity(),
                parkingLot.getPriceHour(),
                addressDtoOutput,
                firstName,
                lastName,
                phone,
                mobilePhone,
                email,
                divisionId,
                divisionName
        );

    }
}
