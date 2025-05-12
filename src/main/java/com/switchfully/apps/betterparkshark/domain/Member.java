package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @SequenceGenerator(sequenceName = "member_seq", name = "member_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")

    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Transient
    private String name;

    @Column(name = "phone")
    @NonNull
    private String phone;


    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "registration_date", columnDefinition = "DATE", nullable = false)
    private LocalDate registrationDate;

    @Column(name ="address_id")
    private Long addressId;

    @Column(name ="membership_level_id",nullable = false)
    private Long membershipLevelId;

    public Member() {}
    public Member(String firstName, String lastName, String phone, String email, String password, String licensePlate, LocalDate registrationDate, Long addressId, Long membershipLevelId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.name = firstName + " " + lastName;
        this.email = email;
        this.password = password;
        this.licensePlate = licensePlate;
        this.registrationDate = registrationDate;
        this.addressId = addressId;
        this.membershipLevelId = membershipLevelId;
    }

    public void setMembershipLevelId(long membershipLevelId) {
        this.membershipLevelId = membershipLevelId;
    }

    public Long getId() {
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Long getAddressId() {
        return addressId;
    }

    public Long getMembershipLevelId() {
        return membershipLevelId;
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
        return this.id == member.id;
    }
}
