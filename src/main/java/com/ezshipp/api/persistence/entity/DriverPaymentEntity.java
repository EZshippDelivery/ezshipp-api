package com.ezshipp.api.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_payment",  catalog = "")
@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
public class DriverPaymentEntity extends Auditable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "delivery_amount", nullable = false, precision = 2)
    private Double deliveryAmount;
    @Column(name = "login_hour_amount", nullable = true, precision = 2)
    private Double loginHourAmount;
    @Column(name = "penalty_amount", nullable = true, precision = 2)
    private Double penaltyAmount;
    @Column(name = "total_incentives", nullable = true, precision = 2)
    private Double totalIncentives;
    @Column(name = "pay_date", nullable = false)
    private Date payDate;
    @Column(name = "driver_id", nullable = false, insertable = false, updatable = false)
    private int driverId;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private DriverEntity driverByDriverId;
}
