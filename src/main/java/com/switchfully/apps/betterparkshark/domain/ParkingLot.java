package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_lot_seq")
    @SequenceGenerator(sequenceName = "parking_lot_seq", name = "parking_lot_seq",allocationSize=1)
    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "lot_category",nullable = false)
    @Enumerated(EnumType.STRING)
    private LotCategory lotCategory;

    @Column(name = "max_capacity",nullable = false)
    private int maxCapacity;

    @Column(name = "price_hour")
    private double priceHour;

    @Column(name = "contact_person",nullable = false)
    private long contactPersonId;

    @Column(name = "address_id",nullable = false)
    private long addressId;

    @Column(name = "division_id",nullable = false)
    private long divisionId;

    public ParkingLot() {
    }

    public ParkingLot(String name, LotCategory lotCategory, int maxCapacity, double priceHour, long contactPersonId, long addressId, long divisionId) {
        this.name = name;
        this.lotCategory = lotCategory;
        this.maxCapacity = maxCapacity;
        this.priceHour = priceHour;
        this.contactPersonId = contactPersonId;
        this.addressId = addressId;
        this.divisionId = divisionId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LotCategory getLotCategory() {
        return lotCategory;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public double getPriceHour() {
        return priceHour;
    }

    public long getAddressId() {
        return addressId;
    }

    public long getContactPersonId() {
        return contactPersonId;
    }

    public long getDivisionId() {
        return divisionId;
    }
}
