package com.switchfully.apps.betterparkshark.webapi.dto;

import java.sql.Timestamp;

public class AllocationDtoOutput {

    private Long allocationId;
    private Long memberId;
    private String licensePlate;
    private Long parkingId;
    private Timestamp startTime;
    private Timestamp endTime;

    public AllocationDtoOutput() {

    }

    public AllocationDtoOutput(Long allocationId, Long memberId, String licensePlate, Long parkingId, Timestamp startTime, Timestamp endTime) {
        this.allocationId = allocationId;
        this.memberId = memberId;
        this.licensePlate = licensePlate;
        this.parkingId = parkingId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
