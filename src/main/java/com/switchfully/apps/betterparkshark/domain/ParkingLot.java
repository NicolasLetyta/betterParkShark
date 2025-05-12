package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_lot_seq")
    @SequenceGenerator(sequenceName = "parking_lot_seq", name = "parking_lot_seq",allocationSize=1)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "lot_category",nullable = false)
    private LotCategory lotCategory;

    @Column(name = "max_capacity",nullable = false)
    private int maxCapacity;

    @Column(name = "space_available")
    private int spaceAvailable;

    @Column(name = "price_hour")
    private double priceHour;

    @Column(name = "contact_person",nullable = false)
    private Long contactPersonId;

    @Column(name = "address_id",nullable = false)
    private Long addressId;

    @Column(name = "division_id",nullable = false)
    private Long divisionId;

    public ParkingLot() {
    }

    public ParkingLot(String name, LotCategory lotCategory, int maxCapacity, double priceHour, Long contactPersonId, Long addressId, Long divisionId) {
        this.name = name;
        this.lotCategory = lotCategory;
        this.maxCapacity = maxCapacity;
        this.spaceAvailable = maxCapacity;
        this.priceHour = priceHour;
        this.contactPersonId = contactPersonId;
        this.addressId = addressId;
        this.divisionId = divisionId;
    }

    public Long getId() {
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

    public Long getAddressId() {
        return addressId;
    }

    public Long getContactPersonId() {
        return contactPersonId;
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public int getSpaceAvailable() {
        return spaceAvailable;
    }

    public void setSpaceAvailable(int spaceAvailable) {
        this.spaceAvailable = spaceAvailable;
    }
}
