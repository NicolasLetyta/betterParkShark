package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "allocation")
public class Allocation {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allocation_seq")
    @SequenceGenerator(sequenceName = "allocation_seq", name = "allocation_seq",allocationSize=1)
    @Id
    private Long id;

    @Column(name = "member_id",nullable = false)
    private Long memberId;

    @Column(name = "license_plate",nullable = false)
    private String licensePlate;

    @Column(name = "parking_id",nullable = false)
    private Long parkingId;

    @Column(name = "start_time",nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public Allocation() {
    }

    public Allocation(Long memberId, String licensePlate, Long parkingId) {
        this.memberId = memberId;
        this.licensePlate = licensePlate;
        this.parkingId = parkingId;
        this.startTime = LocalDateTime.now().withNano(0);
    }

    public Long getId() {
        return id;
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

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
