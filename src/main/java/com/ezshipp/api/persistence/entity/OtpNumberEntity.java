package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "otp_number",  catalog = "")
@Setter
@Getter
public class OtpNumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "contact", length = 13)
    private String contact;
    @Column(name = "verification_code", length = 15, nullable = false)
    private String verificationCode;
    @Column(name = "verified")
    private boolean verified;
    @Column(name = "sent_date")
    private Timestamp sentDate;
    @Column(name = "api_message", length = 200)
    private String apiMessage;
}

