package com.ezshipp.api.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_comment",  catalog = "")
@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
public class DriverCommentEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "comments", nullable = true, length = 250)
    private String comments;
    @Column(name = "order_id", nullable = false, updatable = false, insertable = false)
    private int orderId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id",  nullable = false)
    @JsonIgnore
    private OrderEntity orderByOrderId;
}
