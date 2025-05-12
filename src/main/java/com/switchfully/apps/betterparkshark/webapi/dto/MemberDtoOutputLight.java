package com.switchfully.apps.betterparkshark.webapi.dto;

import java.time.LocalDate;

public class MemberDtoOutputLight {
    private long id;
    private String name;
    private String licensePlate;
    private String phone;
    private String email;
    private LocalDate registrationDate;

    public MemberDtoOutputLight(long id, String name, String licensePlate, String phone, String email, LocalDate registrationDate) {
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

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return this.id +" "+ this.name +" "+ this.licensePlate +" "+ this.phone +" "+ this.email ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDtoOutputLight that = (MemberDtoOutputLight) o;
        return this.id == that.id &&
                this.name.equals(that.name) &&
                this.licensePlate.equals(that.licensePlate) &&
                this.phone.equals(that.phone) &&
                this.email.equals(that.email) &&
                this.registrationDate.equals(that.registrationDate);
    }
}
