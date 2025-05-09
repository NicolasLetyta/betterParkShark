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
    private long id;

    @Column(name = "member_id",nullable = false)
    private long memberId;

    @Column(name = "license_plate",nullable = false)
    private String licensePlate;

    @Column(name = "parking_id",nullable = false)
    private long parkingId;

    @Column(name = "start_time",nullable = false)
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    public Allocation() {
    }

    public Allocation(long memberId, String licensePlate, long parkingId) {
        this.memberId = memberId;
        this.licensePlate = licensePlate;
        this.parkingId = parkingId;
        this.startTime = Timestamp.valueOf(LocalDateTime.now().withNano(0));
    }

    public long getId() {
        return id;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
