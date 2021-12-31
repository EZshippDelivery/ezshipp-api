package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer",  catalog = "")
@Setter
@Getter
public class CustomerEntity {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private Integer id;
	    @Column(name = "customer_id")
	    private String customerId;
	    @Column(name = "notify_receiver")
	    private boolean notifyReceiver;
	    @Column(name = "is_premium")
	    private boolean premium;
	    @Column(name = "is_web_signup")
	    private boolean webSignUp;
	    @Column(name = "device_id", updatable = false, insertable = false)
	    private Integer deviceId;
	    @Column(name = "signup_date", nullable = false)
	    private Timestamp signUpDate;
	    @Column(name = "referral_code", length = 15)
	    private String referralCode;
	    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
	    private Integer userId;
	    @Column(name = "modified_by", nullable = false)
	    private Integer modifiedBy;
	    @Column(name = "created_by")
	    private Integer createdBy;
	    @Column(name = "created_time")
	    private Timestamp createdTime;
	    @Column(name = "modified_time", nullable = false)
	    private Timestamp modifiedTime;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "device_id", referencedColumnName = "id")
	    private DeviceEntity deviceByDeviceId;

	    @OneToMany(mappedBy = "customerByCustomerId")
	    private Collection<CustomerInvoiceEntity> customerInvoicesById;

	    @OneToMany(mappedBy = "customerByCustomerId")
	    private Collection<CustomerPricingEntity> customerPricingsById;

	    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	    @JoinColumn(name = "user_id", nullable = false)
	    @JsonIgnore
	    private UserEntity userByUserId;
}
