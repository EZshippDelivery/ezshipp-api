package com.ezshipp.api.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "premium_customer",  catalog = "")
@Setter
@Getter
public class PremiumCustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "customer_id", nullable = false, insertable = false, updatable = false)
    private Integer customerId;
    @Column(name = "pick_address_id", nullable = false, insertable = false, updatable = false)
    private Integer pickupAddressId;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "notify_sms")
    private boolean notifySms;
    @Column(name = "notify_email")
    private boolean notifyEmail;
    @Column(name = "notify_push")
    private boolean notifyPush;
    @Column(name = "gst", length = 20)
    private String gst;
    @Column(name = "is_active")
    private boolean active;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private CustomerEntity customerByCustomerId;

    @OneToOne
    @JoinColumn(name = "pick_address_id", referencedColumnName = "id", nullable = false)
    private AddressEntity addressByPickupAddress;
}
