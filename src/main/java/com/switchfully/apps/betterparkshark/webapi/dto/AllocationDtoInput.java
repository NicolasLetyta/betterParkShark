package com.switchfully.apps.betterparkshark.webapi.dto;

public class AllocationDtoInput {

    private Long memberId;
    private String licensePlate;
    private Long parkingId;

    public AllocationDtoInput() {

    }

    public AllocationDtoInput(Long memberId, String licensePlate, Long parkingId) {
        this.memberId = memberId;
        this.licensePlate = licensePlate;
        this.parkingId = parkingId;
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

    @Override
    public String toString() {
        return "AllocationDtoInput{" +
                "memberId=" + memberId +
                ", licensePlate='" + licensePlate + '\'' +
                ", parkingId=" + parkingId +
                '}';
    }
}
