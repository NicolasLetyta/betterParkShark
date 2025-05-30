package com.switchfully.apps.betterparkshark.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "membership_level")
public class MembershipLevel {
    @Id
    @SequenceGenerator(sequenceName = "membership_level_seq", name = "membership_level_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_level_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "monthly_price", nullable = false)
    private double monthlyPrice;

    @Column(name = "reduction_hour", nullable = false)
    private double reductionHour;

    @Column(name = "max_hour", nullable = false)
    private double maxHour;

    public MembershipLevel() {}
    public MembershipLevel(String name, double monthlyPrice, double reductionHour, double maxHour) {
        this.name = name;
        this.monthlyPrice = monthlyPrice;
        this.reductionHour = reductionHour;
        this.maxHour = maxHour;
    }

    public Long getId() {
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

    @Override
    public String toString() {
        return this.id +" "+this.name + " " + this.monthlyPrice + " " + this.reductionHour + " " + this.maxHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembershipLevel that = (MembershipLevel) o;
        return this.id == that.id;
    }
}
