package com.ezshipp.api.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "offer_used_details",  catalog = "")
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OfferUsedDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "offer_id")
    private int offerId;
    @Column(name = "customer_id", nullable = false)
    private int customerId;
    @Column(name = "offer_used_date", nullable = true)
    private Timestamp offerUsedDate;
    @Column(name = "created_time", nullable = false, insertable = false, updatable = false)
    private Timestamp createdTime;
    
}

