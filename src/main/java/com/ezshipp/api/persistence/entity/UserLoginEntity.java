package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ezshipp.api.enums.LoginSource;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_login",  catalog = "")
@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
public class UserLoginEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private int id;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "login_source", nullable = false)
	    private LoginSource loginSource;
	    @Column(name = "last_login_time", nullable = false)
	    private Timestamp lastLoginTime;
	    @Column(name = "session", nullable = false, length = 200)
	    private String session;
	    @Column(name = "expired")
	    private boolean expired;
	    @Column(name = "app_version", length = 15)
	    private String appVersion;
	    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
	    private int userId;

	    @ManyToOne(fetch = FetchType.EAGER, optional = false)
	    @JoinColumn(name = "user_id", nullable = false)
	    @JsonIgnore
	    private UserEntity userByUserId;
}
