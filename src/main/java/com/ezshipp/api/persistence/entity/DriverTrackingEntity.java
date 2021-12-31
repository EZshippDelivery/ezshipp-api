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
@Table(name = "driver_tracking",  catalog = "")
@Setter
@Getter
public class DriverTrackingEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Temporal(TemporalType.DATE)
    @Column(name = "track_date", nullable = false)
    private Date trackDate;
    @Column(name = "last_longitude", nullable = false, precision = 6)
    private double lastLongitude;
    @Column(name = "last_latitude", nullable = false, precision = 6)
    private double lastLatitude;
    @Column(name = "kms", nullable = false, precision = 2)
    private double kms;
    @Column(name = "idle_time", nullable = false, precision = 2)
    private int idleTime;
    @Column(name = "battery_percentage", nullable = false)
    private int batteryPercentage;
    @Column(name = "online_mode")
    private boolean onlineMode;
    @Column(name = "last_updated_time", nullable = false)
    private Timestamp lastUpdatedTime;
    @Column(name = "driver_id", nullable = false, insertable = false, updatable = false)
    private int driverId;
    @Column(name = "created_by", nullable = false)
    private int createdBy;
    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime;
    @Column(name = "modified_by", nullable = false)
    private int modifiedBy;
    @Column(name = "modified_time", nullable = false)
    private Timestamp modifiedTime;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private DriverEntity driverByDriverId;


}

