package com.ezshipp.api.persistence.entity;

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

import com.ezshipp.api.enums.AddressType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_address",  catalog = "")
@Setter
@Getter
public class CustomerAddressEntity extends Auditable<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "customer_id", nullable = true, insertable = false, updatable = false)
    private Integer customerId;
    @Column(name = "address_id", nullable = true, insertable = false, updatable = false)
    private Integer addressId;
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType addressType;
  
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private CustomerEntity customerByCustomerId;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private AddressEntity addressByAddressId;
}
