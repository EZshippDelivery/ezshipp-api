package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_due",  catalog = "")
@Setter
@Getter
public class DriverDueEntity {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private int id;
	    @Column(name = "delivery_amount", nullable = false, precision = 2)
	    private double deliveryAmount;
	    @Column(name = "cod_amount", nullable = true, precision = 2)
	    private Double codAmount;
	    @Temporal(TemporalType.DATE)
	    @Column(name = "recorded_date", nullable = false)
	    private Date recordedDate;
	    @Column(name = "driver_id", nullable = false, insertable = false, updatable = false)
	    private int driverId;
	    @Column(name = "modified_by", nullable = false)
	    private Integer modifiedBy;
	    @Column(name = "created_by", nullable = true)
	    private Integer createdBy;
	    @Column(name = "created_time", nullable = true)
	    private Timestamp createdTime;
	    @Column(name = "modified_time", nullable = false)
	    private Timestamp modifiedTime;

	    @ManyToOne
	    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
	    private DriverEntity driverByDriverId;
}
