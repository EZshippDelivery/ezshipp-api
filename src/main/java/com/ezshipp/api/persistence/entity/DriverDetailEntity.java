package com.ezshipp.api.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_detail",  catalog = "")
@Setter
@Getter
public class DriverDetailEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "driver_seq_id")
    private Integer driverSeqId;
    @Column(name = "license_image_url")
    private String licenseUrl;
    @Column(name = "license")
    private String license;
    @Column(name = "driver_id", nullable = false, insertable = false, updatable = false)
    private int driverId;
    @Column(name = "number_plate")
    private String numberPlate;
    @Column(name = "zone_id", nullable = false, insertable = false, updatable = false)
    private int zoneId;
    @Column(name = "shift_id", nullable = false, insertable = false, updatable = false)
    private int shiftId;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private DriverEntity driverByDriverId;
    @ManyToOne
    @JoinColumn(name = "zone_id", referencedColumnName = "id", nullable = false)
    private ZoneEntity zoneByZoneId;

    @ManyToOne
    @JoinColumn(name = "shift_id", referencedColumnName = "id", nullable = false)
    private ShiftEntity shiftByShiftId;
}
