package com.ezshipp.api.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address",  catalog = "")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AddressEntity extends Auditable<Integer>{

	    @Id
	    @Column(name = "id", nullable = false)
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    @Column(name = "address1", nullable = false, length = 300)
	    private String address1;
	    @Column(name = "address2", length = 300)
	    private String address2;
	    @Column(name = "city", nullable = false, length = 75)
	    private String city;
	    @Column(name = "state", nullable = false, length = 30)
	    private String state;
	    @Column(name = "longitude", nullable = false, precision = 6)
	    private double longitude;
	    @Column(name = "latitude", nullable = false, precision = 6)
	    private double latitude;
	    @Column(name = "apartment", length = 100)
	    private String apartment;
	    @Column(name = "complex_name", length = 100)
	    private String complexName;
	    @Column(name = "landmark", length = 100)
	    private String landmark;
	    @Column(name = "pincode", nullable = false)
	    private Long pincode;
	    @Column(name = "is_commercial")
	    private boolean commercial;
}
