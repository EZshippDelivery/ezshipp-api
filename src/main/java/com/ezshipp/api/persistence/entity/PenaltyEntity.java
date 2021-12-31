package com.ezshipp.api.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "penalty",  catalog = "")
@Setter
@Getter
public class PenaltyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "price", precision = 2)
    private Double price;
    @Column(name = "is_deleted")
    private boolean deleted;

//    @OneToMany(mappedBy = "penaltyByPenaltyId")
//    private Collection<DriverPenaltyCountEntity> driverPenaltyCountsById;

}
