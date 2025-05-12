package com.switchfully.apps.betterparkshark.webapi.dto;


public class ParkingLotDtoOutput {

    private Long id;
    private String name;
    private int maxCapacity;
    private String employeePhone;
    private String employeeMobile;
    private String employeeEmail;

    public ParkingLotDtoOutput() {
    }

    public ParkingLotDtoOutput(Long id, String name, int maxCapacity, String employeePhone, String employeeMobile, String employeeEmail) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.employeePhone = employeePhone;
        this.employeeMobile = employeeMobile;
        this.employeeEmail = employeeEmail;
    }

    public ParkingLotDtoOutput(Long id, String name, int maxCapacity, String employeePhone, String employeeEmail) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
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
}
