package com.ezshipp.api.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_status",  catalog = "")
@Setter
@Getter
public class OrderStatusEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = true, length = 20)
    private String name;
    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;

}
