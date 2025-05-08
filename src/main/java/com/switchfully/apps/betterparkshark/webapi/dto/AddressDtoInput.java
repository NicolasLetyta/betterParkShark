package com.switchfully.apps.betterparkshark.webapi.dto;

public class AddressDtoInput {
    private String street;
    private String streetNumber;
    private String postalCode;
    private String city;
    private String country;

    public AddressDtoInput(String street, String streetNumber, String postalCode, String city, String country) {
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
}
