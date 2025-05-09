package com.switchfully.apps.betterparkshark.webapi.dto;

public class AllocationDtoInput {

    private long memberId;
    private String licensePlate;
    private long parkingId;

    public AllocationDtoInput() {

    }

    public AllocationDtoInput(Long memberId, String licensePlate, long parkingId) {
        this.memberId = memberId;
        this.licensePlate = licensePlate;
        this.parkingId = parkingId;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public long getParkingId() {
        return parkingId;
    }
}
