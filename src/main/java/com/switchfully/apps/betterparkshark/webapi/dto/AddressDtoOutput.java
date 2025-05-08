package com.switchfully.apps.betterparkshark.webapi.dto;

import java.util.Objects;

public class AddressDtoOutput {
    private String street;
    private String streetNumber;
    private String postalCode;
    private String city;
    private String country;

    public AddressDtoOutput(long id, String street, String streetNumber, String postalCode, String city, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return this.street + " " + this.streetNumber + " " + this.postalCode + " " + this.city + " " + this.country;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDtoOutput that = (AddressDtoOutput) o;
        return Objects.equals(this.street, that.street) &&
                Objects.equals(this.streetNumber, that.streetNumber) &&
                Objects.equals(this.postalCode, that.postalCode) &&
                Objects.equals(this.city, that.city) &&
                Objects.equals(this.country, that.country);
    }

}
