package com.switchfully.apps.betterparkshark.repository;


import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MemberProjection {
    long getId();
    String getFirstName();
    String getLastName();
    String getLicensePlate();
    String getPhone();
    String getEmail();
    LocalDate getRegistrationDate();

    default String getName() {
        return getFirstName() + " " + getLastName();
    }

}
