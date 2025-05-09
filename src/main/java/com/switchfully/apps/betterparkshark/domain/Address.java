package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @SequenceGenerator(sequenceName = "address_seq", name = "address_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    private long id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "postal_code",nullable = false)
    private String postalCode;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "country",nullable = false)
    private String country;

    public Address() {}
    public Address(String street, String streetNumber, String postalCode, String city, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    @NonNull
    public String getPostalCode() {
        return postalCode;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id;
    }
    @Override
    public String toString() {
        return this.id + " " + street + " " + streetNumber + " " + postalCode + " " + city + " " + country;
    }
}
