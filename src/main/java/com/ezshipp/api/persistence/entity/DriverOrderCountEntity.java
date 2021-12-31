package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_order_count")
@Setter
@Getter
public class DriverOrderCountEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private int id;
	    @Column(name = "picked_count", nullable = false)
	    private int pickedCount;
	    @Column(name = "delivered_count", nullable = false)
	    private int deliveredCount;
	    @Column(name = "extra_kms", nullable = true)
	    private Integer extraKms;
	    @Column(name = "extra_hour", nullable = true)
	    private Integer extraHour;
	    @Column(name = "total_login_hours", nullable = true)
	    private Integer totalLoginHours;
	    @Column(name = "total_order_count", nullable = true)
	    private Integer totalOrderCount;
	    @Column(name = "exchange_count", nullable = true)
	    private Integer exchangeCount;
	    @Temporal(TemporalType.DATE)
	    @Column(name = "tracking_date", nullable = false)
	    private Date trackingDate;
	    @Column(name = "modified_by", nullable = false)
	    private Integer modifiedBy;
	    @Column(name = "created_by", nullable = true)
	    private Integer createdBy;
	    @Column(name = "created_time", nullable = true)
	    private Timestamp createdTime;
	    @Column(name = "modified_time", nullable = false)
	    private Timestamp modifiedTime;

	    @Column(name = "driver_id", nullable = false, insertable = false, updatable = false)
	    private int driverId;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "driver_id", nullable = false)
	    @JsonIgnore
	    private DriverEntity driverByDriverId;

}
