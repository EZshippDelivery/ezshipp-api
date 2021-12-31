package com.ezshipp.api.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.ezshipp.api.enums.PricingType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_pricing",  catalog = "")
@Setter
@Getter
public class CustomerPricingEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private int id;
	    @Column(name = "min_order_count", nullable = true)
	    private Integer minOrderCount;
	    @Column(name = "km_range_1", nullable = true)
	    private Integer kmRange1;
	    @Column(name = "km_range_2", nullable = true)
	    private Integer kmRange2;
	    @Column(name = "km_range_3", nullable = true)
	    private Integer kmRange3;
	    @Column(name = "price_per_km_range_1", precision = 2)
	    private Double pricePerKmRange1;
	    @Column(name = "price_per_km_range_2", precision = 2)
	    private Double pricePerKmRange2;
	    @Column(name = "price_per_km_range_3", precision = 2)
	    private Double pricePerKmRange3;
	    @Column(name = "km_range_4", nullable = true)
	    private Integer kmRange4;
	    @Column(name = "price_per_km_range_4", precision = 2)
	    private Double pricePerKmRange4;
	    @Column(name = "price_per_extra_weight", precision = 2)
	    private Double pricePerExtraWeight;
	    @Column(name = "max_kms", nullable = true)
	    private Integer maxKms;
	    @Column(name = "apply_tax")
	    private boolean applyTax;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "pricing_type")
	    private PricingType pricingType;
	    @Column(name = "same_day", precision = 2)
	    private Double sameDay;
	    @Column(name = "four_hours", precision = 2)
	    private Double fourHours;
	    @Column(name = "instant", precision = 2)
	    private Double instant;
	    @Column(name = "modified_by", nullable = false)
	    private Integer modifiedBy;
	    @Column(name = "created_by", nullable = true)
	    private Integer createdBy;
	    @Column(name = "created_time", nullable = true)
	    
	    private Date createdTime;
	    
	    @Column(name = "modified_time", nullable = false)
	    private Date modifiedTime;

	    @ManyToOne
	    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
	    private CustomerEntity customerByCustomerId;
	    @Column(name = "customer_id", nullable = false, updatable = false, insertable = false)
	    private int customerId;

	    @ManyToOne
	    @JoinColumn(name = "booking_id", referencedColumnName = "id", nullable = false)
	    private BookingTypeEntity bookingByBookingId;
	    @Column(name = "booking_id", nullable = false, updatable = false, insertable = false)
	    private int bookingId;
}
