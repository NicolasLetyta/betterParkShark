package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @SequenceGenerator(sequenceName = "member_seq", name = "member_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

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

    @JoinColumn(name ="address_id")
    private Long address;

    @JoinColumn(name ="membership_level_id",nullable = false)
    private long membershipLevel;

    public Member() {}
    public Member(String firstName, String lastName, String phone, String email, String password, String licensePlate, LocalDate registrationDate, Long address, Long membershipLevel) {
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

    public void setMembershipLevel(Long membershipLevelId) {
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Long getAddress() {
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
        return this.id == member.id;
    }
}
