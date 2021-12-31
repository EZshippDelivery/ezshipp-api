package com.ezshipp.api.persistence.entity;

import java.util.Date;

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
@Table(name = "order_comment",  catalog = "")
@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
public class OrderCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "comments", nullable = true, length = 250)
    private String comments;
    @Column(name = "order_id", nullable = false, updatable = false, insertable = false)
    private int orderId;

    @Column(name = "created_by")
    private int createdBy;
    
    @Column(name = "created_time", nullable = false)
    private Date creationDate;
    
    @Column(name = "modified_by", nullable = false)
    private int lastModifiedBy;
    @Column(name = "modified_time", nullable = false)
    private Date lastModifiedDate;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private OrderEntity orderByOrderId;

}
