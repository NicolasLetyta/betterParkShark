package com.switchfully.apps.betterparkshark.webapi.dto;

import java.time.LocalDateTime;

public class MemberDtoOutput {
    private long id;
    private String name;
    private String licensePlate;
    private int phone;
    private String email;
    private LocalDateTime registrationDate;

    public MemberDtoOutput(long id, String name, String licensePlate, int phone, String email, LocalDateTime registrationDate) {
        this.id = id;
        this.name = name;
        this.licensePlate = licensePlate;
        this.phone = phone;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id)+ " " + this.name + " " + this.email + " " + this.registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDtoOutput that = (MemberDtoOutput) o;
        return this.id == that.id &&
                this.name.equals(that.name) &&
                this.licensePlate.equals(that.licensePlate) &&
                this.phone == that.phone &&
                this.email.equals(that.email) &&
                this.registrationDate.equals(that.registrationDate);
    }
}
