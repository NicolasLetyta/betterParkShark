package com.switchfully.apps.betterparkshark.webapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class MemberDtoOutput {
    private long id;
    private String name;
    private String phone;
    private String email;
    private String licensePlate;
    private LocalDate registrationDate;
    private AddressDtoOutput address;
    private String memberShipLevel;

    public MemberDtoOutput(long id, String name, String phone, String email, String licensePlate, LocalDate registrationDate, AddressDtoOutput address, String memberShipLevel) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.licensePlate = licensePlate;
        this.registrationDate = registrationDate;
        this.address = address;
        this.memberShipLevel = memberShipLevel;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public AddressDtoOutput getAddress() {
        return address;
    }

    public String getMemberShipLevel() {
        return memberShipLevel;
    }

    @Override
    public String toString() {
        return this.id+" "+this.name+" "+this.phone+" "+this.email+" "+this.licensePlate+" "+this.registrationDate+" "+this.address+" "+this.memberShipLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDtoOutput that = (MemberDtoOutput) o;
        return this.id == that.id &&
                this.name.equals(that.name) &&
                this.phone.equals(that.phone) &&
                this.email.equals(that.email) &&
                this.licensePlate.equals(that.licensePlate) &&
                this.registrationDate.equals(that.registrationDate) &&
                this.memberShipLevel.equals(that.memberShipLevel);
    }
}
