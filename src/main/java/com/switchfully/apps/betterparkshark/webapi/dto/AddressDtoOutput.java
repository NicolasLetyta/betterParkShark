package com.switchfully.apps.betterparkshark.webapi.dto;

import java.util.Objects;

public class AddressDtoOutput {
    private String street;
    private String streetNumber;
    private String postalCodeArea;
    private String city;
    private String country;

    public AddressDtoOutput(String street, String streetNumber, String postalCodeArea, String city, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCodeArea = postalCodeArea;
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostalCodeArea() {
        return postalCodeArea;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return this.street + " " + this.streetNumber + " " + this.postalCodeArea + " " + this.city + " " + this.country;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDtoOutput that = (AddressDtoOutput) o;
        return Objects.equals(this.street, that.street) &&
                Objects.equals(this.streetNumber, that.streetNumber) &&
                Objects.equals(this.postalCodeArea, that.postalCodeArea) &&
                Objects.equals(this.city, that.city) &&
                Objects.equals(this.country, that.country);
    }

}
