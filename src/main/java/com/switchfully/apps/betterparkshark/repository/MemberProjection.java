package com.switchfully.apps.betterparkshark.repository;

import java.time.LocalDateTime;

public interface MemberProjection {
    long getId();
    String getName();
    String getLicensePlate();
    String getPhone();
    String getEmail();
    LocalDateTime getRegistrationDate();

}
