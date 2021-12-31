package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ezshipp.api.enums.DeviceTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "device",  catalog = "")
@Setter
@Getter
public class DeviceEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private int id;
	    @Column(name = "device_id", nullable = false, length = 40)
	    private String deviceId;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "device_type", nullable = false, length = 20)
	    private DeviceTypeEnum deviceType;
	    @Column(name = "device_token", nullable = true, length = 200)
	    private String deviceToken;
	    @Column(name = "device_make", nullable = false, length = 45)
	    private String deviceMake;
	    @Column(name = "device_model", nullable = false, length = 45)
	    private String deviceModel;
	    @Column(name = "os", nullable = false, length = 15)
	    private String os;
	    @Column(name = "is_active", nullable = false)
	    private boolean active;

	    @Column(name = "created_by")
	    private int createdBy;
	    @Column(name = "created_time", nullable = false)
	    private Timestamp createdTime;
	    @Column(name = "modified_by")
	    private int modifiedBy;
	    @Column(name = "modified_time", nullable = false)
	    private Timestamp modifiedTime;
}
