package com.switchfully.apps.betterparkshark.webapi.dto;

public class MemberDtoInput {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String licensePlate;
    private AddressDtoInput addressDtoInput;
    private Integer memberShipLevel;

    public MemberDtoInput(String firstName, String lastName, String phone, String email, String password,
                          String licensePlate, AddressDtoInput addressDtoInput, Integer memberShipLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.licensePlate = licensePlate;
        this.addressDtoInput = addressDtoInput;
        this.memberShipLevel = memberShipLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public AddressDtoInput getAddressDtoInput() {
        return addressDtoInput;
    }

    public Integer getMemberShipLevel() {
        return memberShipLevel;
    }
}
