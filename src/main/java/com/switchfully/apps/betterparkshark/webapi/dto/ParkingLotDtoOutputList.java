package com.switchfully.apps.betterparkshark.webapi.dto;

import com.switchfully.apps.betterparkshark.domain.LotCategory;

public class ParkingLotDtoOutputList {

    private long id;
    private String name;
    private LotCategory category;
    private int maxCapacity;
    private int spaceAvailable;
    private double priceHour;
    private AddressDtoOutput addressParkingLot;
    private String employeeFirstName;
    private String employeeLastName;
    private String employeePhone;
    private String employeeMobile;
    private String employeeEmail;
    private long divisionId;
    private String divisionName;

    public ParkingLotDtoOutputList() {
    }

    public ParkingLotDtoOutputList(long id, String name, LotCategory category, int maxCapacity, int spaceAvailable,double priceHour, AddressDtoOutput addressParkingLot, String employeeFirstName, String employeeLastName, String employeePhone, String employeeEmail,long divisionId,  String divisionName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.spaceAvailable = spaceAvailable;
        this.priceHour = priceHour;
        this.addressParkingLot = addressParkingLot;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }

    public ParkingLotDtoOutputList(long id, String name, LotCategory category, int maxCapacity, int spaceAvailable, double priceHour, AddressDtoOutput addressParkingLot, String employeeFirstName, String employeeLastName, String employeePhone, String employeeMobile, String employeeEmail,long divisionId, String divisionName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.spaceAvailable = spaceAvailable;
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

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LotCategory getCategory() {
        return category;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getSpaceAvailable() {
        return spaceAvailable;
    }

    public double getPriceHour() {
        return priceHour;
    }

    public AddressDtoOutput getAddressParkingLot() {
        return addressParkingLot;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public String getEmployeeMobile() {
        return employeeMobile;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public long getDivisionId() {
        return divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }
}
