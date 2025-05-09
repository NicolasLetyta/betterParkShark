package com.switchfully.apps.betterparkshark.webapi.dto;

import com.switchfully.apps.betterparkshark.domain.LotCategory;

public class ParkingLotDtoOutputList {

    private long id;
    private String name;
    private LotCategory category;
    private int maxCapacity;
    private double priceHour;
    private AddressDtoOutput addressParkingLot;
    private String employeeFirstName;
    private String employeeLastName;
    private long employeePhone;
    private long employeeMobile;
    private String employeeEmail;
    private long divisionId;
    private String divisionName;

    public ParkingLotDtoOutputList() {
    }

    public ParkingLotDtoOutputList(long id, String name, LotCategory category, int maxCapacity, double priceHour, AddressDtoOutput addressParkingLot, String employeeFirstName, String employeeLastName, long employeePhone, String employeeEmail,long divisionId,  String divisionName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.priceHour = priceHour;
        this.addressParkingLot = addressParkingLot;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }

    public ParkingLotDtoOutputList(long id, String name, LotCategory category, int maxCapacity, double priceHour, AddressDtoOutput addressParkingLot, String employeeFirstName, String employeeLastName, long employeePhone, long employeeMobile, String employeeEmail,long divisionId, String divisionName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.priceHour = priceHour;
        this.addressParkingLot = addressParkingLot;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeePhone = employeePhone;
        this.employeeMobile = employeeMobile;
        this.employeeEmail = employeeEmail;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }
}
