package com.switchfully.apps.betterparkshark.webapi.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AllocationDtoOutput {

    private Long allocationId;
    private Long memberId;
    private String licensePlate;
    private Long parkingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AllocationDtoOutput() {

    }

    public AllocationDtoOutput(Long allocationId, Long memberId, String licensePlate, Long parkingId, LocalDateTime startTime, LocalDateTime endTime) {
        this.allocationId = allocationId;
        this.memberId = memberId;
        this.licensePlate = licensePlate;
        this.parkingId = parkingId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
