package com.ezshipp.api.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ezshipp.api.enums.EmployeeType;
import com.ezshipp.api.enums.GenderEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee",  catalog = "")
@Setter
@Getter
public class EmployeeEntity {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private int id;
	    @Column(name = "employee_id", nullable = false)
	    private String employeeId;
	    @Column(name = "base_salary", precision = 2)
	    private double baseSalary;
	    @Column(name = "incentives", precision = 2)
	    private double incentives;
//	    @Column(name = "is_active")
//	    private boolean active;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "gender")
	    private GenderEnum gender;
	    @Column(name = "phone_number", nullable = false, length = 75)
	    private String phoneNumber;
	    @Column(name = "dob")
	    private Date dob;
	    @Column(name = "join_date")
	    private Date joinDate;
	    @Column(name = "aadhaar_card_url")
	    private String aadhaarCardUrl;
	    @Column(name = "aadhaar_number")
	    private String aadhaarNumber;
//	    @Column(name = "pan_card_url")
//	    private byte[] panCardUrl;
	    @Column(name = "pan_number")
	    private String panNumber;
	    @Column(name = "bank_name")
	    private String bankName;
	    @Column(name = "bank_ifsc_code")
	    private String bankIfscCode;
	    @Column(name = "bank_account_number")
	    private long bankAccountNumber;
	    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
	    private int userId;
	    @Column(name = "is_part_time", nullable = false)
	    private boolean partTime;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "employee_type")
	    private EmployeeType employeeType;

	    @ManyToOne
	    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	    private UserEntity userByUserId;
}
