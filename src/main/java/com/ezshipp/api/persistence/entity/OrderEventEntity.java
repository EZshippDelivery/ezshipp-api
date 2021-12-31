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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_event",  catalog = "")
@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
public class OrderEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_time", nullable = false)
    private Date lastUpdatedTime;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "driver_id", insertable = false, updatable = false)
    private Integer driverId;
    @Column(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Integer orderId;
    @Column(name = "status_id", nullable = false, insertable = false, updatable = false)
    private Integer statusId;
 
    @Column(name = "created_by", nullable = false, insertable = false, updatable = false)
    private Integer createdBy;
    
    @Column(name = "modified_by", nullable = false, insertable = false, updatable = false)
    private Integer lastModifiedBy;
    
 
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private OrderEntity orderByOrderId;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private DriverEntity driverByDriverId;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private OrderStatusEntity orderStatusByStatusId;

}
