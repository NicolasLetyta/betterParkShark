package com.switchfully.apps.betterparkshark.webapi.dto;

import com.switchfully.apps.betterparkshark.domain.LotCategory;

public class ParkingLotDtoInput {

    private String name;
    private String category;
    private int maxCapacity;
    private double priceHour;
    private AddressDtoInput address;
    private Long contactPersonId;
    private Long divisionId;

    public ParkingLotDtoInput() {
    }

    public ParkingLotDtoInput(String name, String category, int maxCapacity, double priceHour, AddressDtoInput address, Long contactPersonId, Long divisionId) {
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.priceHour = priceHour;
        this.address = address;
        this.contactPersonId = contactPersonId;
        this.divisionId = divisionId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public double getPriceHour() {
        return priceHour;
    }

    public AddressDtoInput getAddress() {
        return address;
    }

    public Long getContactPersonId() {
        return contactPersonId;
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
