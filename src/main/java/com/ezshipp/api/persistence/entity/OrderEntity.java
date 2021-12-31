package com.ezshipp.api.persistence.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.ezshipp.api.enums.OrderSourceType;
import com.ezshipp.api.enums.OrderTypeEnum;
import com.ezshipp.api.enums.PaymentTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`order`",  catalog = "")
@Getter
@Setter
public class OrderEntity{
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private Integer id;
	    @Column(name = "order_seq_id", nullable = false, length = 12)
	    private String orderSeqId;
	    @Column(name = "order_created_time", nullable = true)
	    private Timestamp orderCreatedTime;
	    @Column(name = "order_complete_time")
	    private Timestamp orderCompletedTime;
	    @Column(name = "customer_id", nullable = false, insertable = false, updatable = false)
	    private Integer customerId;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "order_type")
	    private OrderTypeEnum orderType;
	    @Column(name = "item", nullable = false, length = 100)
	    private String item;
	    @Column(name = "item_description", length = 200)
	    private String itemDescription;
	    @Column(name = "item_image")
	    private String itemImage;
	    @Column(name = "sender_name", nullable = false, length = 50)
	    private String senderName;
	    @Column(name = "sender_phone", nullable = false, length = 13)
	    private String senderPhone;
	    @Column(name = "receiver_name", nullable = false, length = 75)
	    private String receiverName;
	    @Column(name = "receiver_phone", nullable = false, length = 13)
	    private String receiverPhone;
	    @Column(name = "pickup_address_id", nullable = false, insertable = false, updatable = false)
	    private Integer pickupAddressId;
	    @Column(name = "pickup_zone_id", nullable = false, insertable = false, updatable = false)
	    private Integer pickupZoneId;
	    @Column(name = "drop_address_id", nullable = false, insertable = false, updatable = false)
	    private Integer dropAddressId;
	    @Column(name = "drop_zone_id", nullable = false, insertable = false, updatable = false)
	    private Integer dropZoneId;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "payment_type")
	    private PaymentTypeEnum paymentType;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "order_source")
	    private OrderSourceType orderSource;
	    @Column(name = "booking_id", nullable = false, insertable = false, updatable = false)
	    private Integer bookingId;
	    @Column(name = "payment_id", insertable = false, updatable = false)
	    private Integer paymentId;
	    @Column(name = "status_id", nullable = false, insertable = false, updatable = false)
	    private Integer statusId;
	    @Column(name = "is_cod")
	    private boolean cod;
	    @Column(name = "delivery_charge", precision = 2)
	    private Double deliveryCharge;
	    @Column(name = "distance", precision = 2)
	    private Double distance;
	    @Column(name = "duration", precision = 2)
	    private Double duration;
	    @Column(name = "cod_charge", precision = 2)
	    private Double codCharge;
	    @Column(name = "future_date")
	    private Date futureDate;
	    @Column(name = "future_pickup_time")
	    private Time futurePickupTime;
	    @Column(name = "future_drop_time")
	    private Time futureDropTime;
	    @Column(name = "item_weight")
	    private Integer weight;
	    @Column(name = "waiting_time")
	    private Integer waitingTime;
	    @Column(name = "customer_signature")
	    private String customerSignature;
	    @Column(name = "delivered_at")
	    private String deliveredAt;
	    @Column(name = "collect_at_pickup")
	    private boolean collectAtPickUp;
	    @Column(name = "barcode")
	    private String barcode;
	    @Column(name = "zoned_id")
	    private Integer zonedId;
	    @Column(name = "driver_id", insertable = false, updatable = false)
	    private Integer driverId;
	    @Column(name = "pick_distance", precision = 2)
	    private Double pickDistance;
	    @Column(name = "pick_duration", precision = 2)
	    private Double pickDuration;
	    @Column(name = "rating_id", nullable = false, insertable = false, updatable = false)
	    private Integer ratingId;
	    @Column(name = "cancellation_reason_id", nullable = false, insertable = false, updatable = false)
	    private Integer cancellationReasonId;
	    @Column(name = "external_id")
	    private String externalId;

	    @Column(name = "amount", precision = 2)
	    private Double amount;
	    @Column(name = "order_accepted")
	    private boolean orderAccepted ;
	    @Column(name = "created_by")
	    private int createdBy;
	    
	    @Column(name = "created_time", nullable = false)
	    private Date creationDate;
	    
	    @Column(name = "modified_by", nullable = false)
	    private int lastModifiedBy;
	    @Column(name = "modified_time", nullable = false)
	    private Date lastModifiedDate;
	    
	    @OneToMany(mappedBy = "orderByOrderId")
	    private Collection<DriverCommentEntity> driverCommentsById;

//	    @OneToMany(mappedBy = "orderByDriverId")
//	    private Collection<DriverDueEntity> driverPaymentsById;

	    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinColumn(name = "customer_id",  nullable = false)
	    @JsonIgnore
	    private CustomerEntity customerByCustomerId;

	    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinColumn(name = "driver_id",  nullable = false)
	    @JsonIgnore
	    private DriverEntity driverByDriverId;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "booking_id", nullable = false)
	    @JsonIgnore
	    private BookingTypeEntity bookingTypeByBookingId;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "payment_id", nullable = false)
	    @JsonIgnore
	    private PaymentEntity paymentByPaymentId;

	    @ManyToOne(fetch = FetchType.EAGER, optional = false)
	    @JoinColumn(name = "pickup_address_id", nullable = false)
	    @JsonIgnore
	    private AddressEntity addressByPickupAddressId;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "pickup_zone_id", nullable = false)
	    @JsonIgnore
	    private ZoneEntity zoneByPickupZoneId;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "status_id", nullable = false)
	    @JsonIgnore
	    private OrderStatusEntity orderStatusByStatusId;

	    @ManyToOne(fetch = FetchType.EAGER, optional = false)
	    @JoinColumn(name = "drop_address_id", nullable = false)
	    @JsonIgnore
	    private AddressEntity addressByDropAddressId;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "drop_zone_id", nullable = false)
	    @JsonIgnore
	    private ZoneEntity zoneByDropZoneId;

	    @OneToMany(mappedBy = "orderByOrderId")
	    private Collection<OrderCommentEntity> orderCommentsById;

	    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderByOrderId")
	    private Collection<OrderEventEntity> orderEventsById;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "rating_id", nullable = false)
	    private DriverRatingEntity ratingByRateId;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "cancellation_reason_id", nullable = false)
	    private CancellationReasonEntity cancellationReasonByReasonId;
}
