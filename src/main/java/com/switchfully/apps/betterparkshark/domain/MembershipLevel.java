package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "membership_level")
public class MembershipLevel {
    @Id
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "monthly_price")
    private double monthlyPrice;

    @Column(name = "reduction_hour")
    private double reductionHour;

    @Column(name = "max_hour")
    private double maxHour;

    public MembershipLevel() {}
    public MembershipLevel(long id, String name, double monthlyPrice, double reductionHour, double maxHour) {
        this.id = id;
        this.name = name;
        this.monthlyPrice = monthlyPrice;
        this.reductionHour = reductionHour;
        this.maxHour = maxHour;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public double getReductionHour() {
        return reductionHour;
    }

    public double getMaxHour() {
        return maxHour;
    }
}
