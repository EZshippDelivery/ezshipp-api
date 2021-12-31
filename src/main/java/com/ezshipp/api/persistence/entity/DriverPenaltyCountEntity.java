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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_penalty_count",  catalog = "")
@Setter
@Getter
public class DriverPenaltyCountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "penalty_date", nullable = false)
    private Date penaltyDate;
    @Column(name = "created_by", nullable = false)
    private int createdBy;
    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime;
    @Column(name = "modified_by", nullable = false)
    private int modifiedBy;
    @Column(name = "modified_time", nullable = false)
    private Timestamp modifiedTime;
    @Column(name = "user_id", nullable = true)
    private Integer userId;
    @Column(name = "penalty_amount", nullable = true, precision = 0)
    private Double penaltyAmount;
    @Column(name = "penalty_count", nullable = true)
    private Integer penaltyCount;
    @Column(name = "penalty_type", nullable = true,length = 255)
    private String penaltyType;

//    @ManyToOne
//    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    @JsonIgnore
    private DriverEntity driverByDriverId;

    @Column(name = "driver_id", nullable = false, insertable = false, updatable = false)
    private int driverId;
    @Column(name = "penalty_id", nullable = false, insertable = false, updatable = false)
    private int penaltyId;

//    @ManyToOne
//    @JoinColumn(name = "penalty_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "penalty_id", nullable = false)
    @JsonIgnore
    private PenaltyEntity penaltyByPenaltyId;

}
