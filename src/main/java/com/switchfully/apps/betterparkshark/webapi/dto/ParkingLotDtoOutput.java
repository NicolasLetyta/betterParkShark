package com.switchfully.apps.betterparkshark.webapi.dto;


public class ParkingLotDtoOutput {

    private long id;
    private String name;
    private int maxCapacity;
    private long employeePhone;
    private long employeeMobile;
    private String employeeEmail;

    public ParkingLotDtoOutput() {
    }

    public ParkingLotDtoOutput(long id, String name, int maxCapacity, long employeePhone, long employeeMobile, String employeeEmail) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.employeePhone = employeePhone;
        this.employeeMobile = employeeMobile;
        this.employeeEmail = employeeEmail;
    }

    public ParkingLotDtoOutput(long id, String name, int maxCapacity, long employeePhone, String employeeEmail) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
    }
}
