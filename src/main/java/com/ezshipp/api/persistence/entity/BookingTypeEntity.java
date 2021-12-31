package com.ezshipp.api.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "booking_type",  catalog = "")
@Setter
@Getter
public class BookingTypeEntity {
	  @Id
	    @Column(name = "id", nullable = false)
	    private int id;
	    @Column(name = "name", length = 50)
	    private String name;
	    @Column(name = "price", precision = 2)
	    private Double price;
	    @Column(name = "is_deleted")
	    private boolean deleted;
}
