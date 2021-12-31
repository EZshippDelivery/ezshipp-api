package com.ezshipp.api.persistence.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.ezshipp.api.enums.AuthType;
import com.ezshipp.api.enums.UserType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user",  catalog = "")
@Setter
@Getter
public class UserEntity {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private Integer id;
	    @Column(name = "first_name", nullable = false, length = 75)
	    private String firstName;
	    @Column(name = "last_name", nullable = false, length = 75)
	    private String lastName;
	    @Column(name = "email", nullable = false, length = 75)
	    private String email;
	    @Column(name = "username", nullable = false, length = 100)
	    private String username;
	    @Column(name = "password", nullable = false, length = 255)
	    private String password;
	    @Column(name = "phone", nullable = false, length = 10)
	    private long phone;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "user_type", nullable = false)
	    private UserType userType;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "auth_type", nullable = false)
	    private AuthType authType;
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
	    @Column(name = "profile_image_url", nullable = true, length = 40)
	    private String profileUrl;
	  
	    @OneToMany(mappedBy = "userByUserId", fetch = FetchType.LAZY)
	    @JsonManagedReference
	    private Collection<DriverEntity> driversById;

	    @OneToMany(mappedBy = "userByUserId", fetch = FetchType.LAZY)
	    private Collection<EmployeeEntity> employeesById;

	    @OneToMany(mappedBy = "userByUserId", fetch = FetchType.LAZY)
	    private Collection<UserLoginEntity> userLoginsById;

	    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    @JoinTable(name = "user_role", joinColumns =
	    @JoinColumn(name = "user_id"), inverseJoinColumns =
	    @JoinColumn(name = "role_id"))
	    private Set<RoleEntity> roles;

	    public UserEntity(UserEntity userEntity) {
	        this.firstName = userEntity.firstName;
	        this.lastName = userEntity.lastName;
	        this.email = userEntity.email;
	        this.password = userEntity.password;
	        this.active = userEntity.active;
	        this.roles = userEntity.roles;
	    }

	    public UserEntity(String firstName, String lastName, String username, String email, String password) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.username = username;
	        this.email = email;
	        this.password = password;
	    }

	    public UserEntity() {}
	    @PrePersist
	    protected void onCreate() {
	        createdTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
	        modifiedTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        modifiedTime = new Timestamp(Calendar.getInstance().getTimeInMillis());

	    }
}
