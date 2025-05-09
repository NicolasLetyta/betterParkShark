package com.switchfully.apps.betterparkshark.webapi.dto;

import com.switchfully.apps.betterparkshark.domain.LotCategory;

public class ParkingLotDtoInput {

    private String name;
    private LotCategory category;
    private int maxCapacity;
    private double priceHour;
    private AddressDtoInput address;
    private long contactPersonId;
    private long divisionId;

    public ParkingLotDtoInput() {
    }

    public ParkingLotDtoInput(String name, LotCategory category, int maxCapacity, double priceHour, AddressDtoInput address, long contactPersonId, long divisionId) {
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

    public LotCategory getCategory() {
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

    public long getContactPersonId() {
        return contactPersonId;
    }

    public long getDivisionId() {
        return divisionId;
    }
}
