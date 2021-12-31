package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_incentive", catalog = "")
@Setter
@Getter
public class  DriverIncentiveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "driver_id", nullable = false)
    private int driverId;
    @Column(name = "base_salary", nullable = false, precision = 2)
    private double baseSalary;
    @Column(name = "base_petrol", nullable = false, precision = 2)
    private double basePetrol;
    @Column(name = "instant_amount", nullable = false, precision = 2)
    private double instantAmount;
    @Column(name = "fourhour_amount", nullable = false, precision = 2)
    private double fourHourAmount;
    @Column(name = "sameday_amount", nullable = false, precision = 2)
    private double sameDayAmount;
    @Column(name = "extra_km_amount", nullable = false, precision = 2)
    private double extraKmAmount;
    @Column(name = "extra_fourhour_amount", nullable = false, precision = 2)
    private double extraFourHourAmount;
    @Column(name = "extra_sameday_amount", nullable = false, precision = 2)
    private double extraSameDayAmount;
    @Column(name = "extra_instant_amount", nullable = false, precision = 2)
    private double extraInstantAmount;
    @Column(name = "extra_hour_amount", nullable = false, precision = 2)
    private double extraHourAmount;
    @Column(name = "min_order_count", nullable = false)
    private int minOrderCount;
    @Column(name = "max_kms", nullable = false)
    private int maxKms;
    @Column(name = "recorded_date", nullable = false)
    private Timestamp recordedDate;
    @Column(name = "login_hour_amount", nullable = false)
    private int loginHourAmount;
    @Column(name = "created_by", nullable = false)
    private int createdBy;
    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime;
    @Column(name = "modified_by", nullable = false)
    private int modifiedBy;
    @Column(name = "modified_time", nullable = false)
    private Timestamp modifiedTime;
    @Column(name = "effective_date", nullable = true)
    private Timestamp effectiveDate;
}
