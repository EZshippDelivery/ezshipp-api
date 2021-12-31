package com.ezshipp.api.persistence.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable<U> {

	

	@CreatedBy
    @Column(name = "created_by")
    protected U createdBy;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name = "created_time", nullable = false)
    protected Date creationDate;

    @LastModifiedBy
    @Column(name = "modified_by", nullable = false)
    protected U lastModifiedBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @Column(name = "modified_time", nullable = false)
    protected Date lastModifiedDate;
}
