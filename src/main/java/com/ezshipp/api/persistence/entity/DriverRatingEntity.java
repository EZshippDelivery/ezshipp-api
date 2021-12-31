package com.ezshipp.api.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_rating",  catalog = "")
@Setter
@Getter
public class DriverRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "driver_id", nullable = false)
    private int driverId;
    @Column(name = "order_id", nullable = false)
    private int orderId;
    @Column(name = "customer_id", nullable = false)
    private int customerId;
    @Column(name = "is_wearing_tshirt")
    private boolean wearingTShirt;
    @Column(name = "is_carrying_bag")
    private boolean carryingBag;
    @Column(name = "is_delivered_properly")
    private boolean deliveredProperly;
    @Column(name = "rating", nullable = false)
    private int rating;
    @Column(name = "rated_date", nullable = false)
    private Date ratedDate;
    @Column(name = "rated_by", nullable = false)
    private int ratedBy;
    @Column(name = "notes", nullable = false)
    private String notes;


}
