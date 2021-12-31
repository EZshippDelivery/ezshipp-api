package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment",  catalog = "")
@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
public class PaymentEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "payment_id", length = 50)
    private String paymentId;
    @Column(name = "customer_id", nullable = true, insertable = false, updatable = false)
    private int customerId;
    @Column(name = "amount", nullable = true, precision = 2)
    private Double amount;
    @Column(name = "is_captured", nullable = true)
    private boolean captured;
    @Column(name = "is_refunded", nullable = true)
    private boolean refunded;
    @Column(name = "capture_time", nullable = false)
    private Timestamp captureTime;
    @Column(name = "refund_time", nullable = false)
    private Timestamp refundTime;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private CustomerEntity customerByCustomerId;
}
