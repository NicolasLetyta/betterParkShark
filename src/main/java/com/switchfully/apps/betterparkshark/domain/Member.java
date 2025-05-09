package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @SequenceGenerator(sequenceName = "address_seq", name = "address_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationDate;


    @JoinColumn(name ="address_id")
    private long address;


    @JoinColumn(name ="membership_level_id")
    private long membershipLevel;

    public Member() {}
    public Member(String firstName, String lastName, String phone, String email, String password, String licensePlate, LocalDateTime registrationDate, long address, long membershipLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.name = firstName + " " + lastName;
        this.email = email;
        this.password = password;
        this.licensePlate = licensePlate;
        this.registrationDate = registrationDate;
        this.address = address;
        this.membershipLevel = membershipLevel;
    }

    public void setMembershipLevel(long membershipLevelId) {
        this.membershipLevel = membershipLevelId;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public String getPassword() {
        return password;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public long getAddress() {
        return address;
    }

    public long getMembershipLevel() {
        return membershipLevel;
    }

    @Override
    public String toString() {
        return this.id + " " + this.firstName + " " + this.lastName + " " + this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id;
    }
}
