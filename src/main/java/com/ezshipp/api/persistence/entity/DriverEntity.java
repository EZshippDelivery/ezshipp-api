package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver", catalog = "")
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DriverEntity {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private Integer id;
	    @Column(name = "is_active")
	    private boolean active;
	    @Column(name = "modified_by", nullable = false)
	    private Integer modifiedBy;
	    @Column(name = "created_by", nullable = true)
	    private Integer createdBy;
	    @Column(name = "created_time", nullable = true)
	    private Timestamp createdTime;
	    @Column(name = "modified_time", nullable = false)
	    private Timestamp modifiedTime;
	    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
	    private Integer userId;
	    @Column(name = "device_id", nullable = false, insertable = false, updatable = false)
	    private Integer deviceId;

	    @ManyToOne
	    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	    @JsonBackReference
	    private UserEntity userByUserId;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "device_id", referencedColumnName = "id")
	    private DeviceEntity deviceByDeviceId;

}
