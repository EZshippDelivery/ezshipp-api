package com.ezshipp.api.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezshipp.api.enums.AuthType;
import com.ezshipp.api.enums.DeviceTypeEnum;
import com.ezshipp.api.enums.RoleEnum;
import com.ezshipp.api.enums.UserType;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.model.ChangePasswordRequest;
import com.ezshipp.api.model.DriverSignUpRequest;
import com.ezshipp.api.model.SignUpRequest;

import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.persistence.entity.DeviceEntity;
import com.ezshipp.api.persistence.entity.DriverEntity;
import com.ezshipp.api.persistence.entity.RoleEntity;
import com.ezshipp.api.persistence.entity.UserEntity;
import com.ezshipp.api.repository.CustomerEntityRepository;
import com.ezshipp.api.repository.DriverEntityRepository;
import com.ezshipp.api.repository.UserRepository;

@Service
public class AuthService {
 
	@Autowired
	RoleService roleService;
	
	@Autowired
	DriverEntityRepository driverEntityRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DeviceEntityService deviceEntityService;
	
	@Autowired
	CustomerEntityRepository customerEntityRepository;
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	public DriverEntity registerDriver(DriverSignUpRequest signUpRequest) throws Exception {
		UserEntity userEntity = registerUser(signUpRequest);

		if (signUpRequest.getUserType() == UserType.DRIVER) {
			// creating driver's device
			DeviceEntity newDevice = createDevice(signUpRequest, userEntity);

			// creating driver's account
			DriverEntity driverEntity = new DriverEntity();
			driverEntity.setActive(false);
			driverEntity.setUserId(userEntity.getId());
			driverEntity.setUserByUserId(userEntity);
			driverEntity.setDeviceId(newDevice.getId());
			driverEntity.setDeviceByDeviceId(newDevice);
			driverEntity.setCreatedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			driverEntity.setModifiedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			driverEntity.setCreatedBy(userEntity.getId());
			driverEntity.setModifiedBy(userEntity.getId());

			return driverEntityRepository.save(driverEntity);
		}
		throw new Exception();
	}
	
	public UserEntity registerUser(SignUpRequest signUpRequest) throws Exception {
		//validate(signUpRequest);

		// Creating user's account
		UserEntity user = new UserEntity(signUpRequest.getFirstName(), signUpRequest.getLastName(), "",
				signUpRequest.getEmail(), signUpRequest.getPassword());
		user.setUserType(signUpRequest.getUserType());
		if (!StringUtils.isEmpty(signUpRequest.getPhone())) {
			user.setPhone(Long.valueOf(signUpRequest.getPhone()));
		}
		user.setActive(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setAuthType(AuthType.SMS);
		RoleEnum roleEnum;
		if (signUpRequest.getUserType() == UserType.DRIVER) {
			roleEnum = RoleEnum.ROLE_DRIVER;
			user.setUsername(signUpRequest.getEmail());
			user.setAuthType(AuthType.EMAIL);
			signUpRequest.setAuthType(AuthType.EMAIL);
			user.setPhone(Long.valueOf(signUpRequest.getPhone()));
			user.setActive(false);
		} else if (signUpRequest.getUserType() == UserType.CUSTOMER) {
			roleEnum = RoleEnum.ROLE_CUSTOMER;
			if (StringUtils.isEmpty(signUpRequest.getPhone())) {
				user.setUsername(signUpRequest.getEmail());
				signUpRequest.setAuthType(AuthType.EMAIL);
				user.setAuthType(AuthType.EMAIL);
			} else {
				user.setUsername(signUpRequest.getPhone());
			}
		} else if (signUpRequest.getUserType() == UserType.EMPLOYEE) {
			roleEnum = RoleEnum.ROLE_ADMIN;
			user.setUsername(signUpRequest.getEmail());
		} else if (signUpRequest.getUserType() == UserType.ADMIN) {
			roleEnum = RoleEnum.ROLE_ADMIN;
			user.setUsername(signUpRequest.getPhone());
		}else{
			roleEnum = RoleEnum.ROLE_USER;
			user.setUsername(signUpRequest.getEmail());
		}
		RoleEntity userRole = (RoleEntity) roleService.findByName(roleEnum)
				.orElseThrow(() -> new Exception("User Role not set."));
		user.setRoles(Collections.singleton(userRole));
		//System.out.println(user.getUserType());
		return userRepository.save(user);
	}
	
	
	public DeviceEntity createDevice(DriverSignUpRequest signUpRequest, UserEntity userEntity) {
		DeviceEntity deviceEntity = new DeviceEntity();
		deviceEntity.setActive(true);
		if (StringUtils.isEmpty(deviceEntity.getDeviceId())) {
			deviceEntity.setDeviceId("DEVICEIDNOTFOUND");
		} else {
			deviceEntity.setDeviceId(signUpRequest.getDeviceId());
		}
		deviceEntity.setDeviceToken(signUpRequest.getDeviceToken());
		deviceEntity.setDeviceType(DeviceTypeEnum.valueOf(signUpRequest.getDeviceType()));
		deviceEntity.setDeviceModel(signUpRequest.getDeviceModel());
		deviceEntity.setDeviceMake(signUpRequest.getDeviceMake());
		deviceEntity.setOs(signUpRequest.getOs());
		deviceEntity.setCreatedBy(userEntity.getId());
		deviceEntity.setModifiedBy(userEntity.getId());
		deviceEntity.setCreatedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		deviceEntity.setModifiedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		return deviceEntityService.createDevice(deviceEntity);
	}
	
	
	public CustomerEntity registerCustomer(DriverSignUpRequest signUpRequest) throws Exception
			{
		UserEntity userEntity = registerUser(signUpRequest);

		if (signUpRequest.getUserType() == UserType.CUSTOMER || signUpRequest.getUserType() == UserType.API || signUpRequest.getUserType() == UserType.ADMIN) {
			// creating customer's device if it is not web sign up
			DeviceEntity newDevice = null;
			if (!StringUtils.isEmpty(signUpRequest.getDeviceType())) {
				if (signUpRequest.getUserType() != UserType.API) {
					newDevice = createDevice(signUpRequest, userEntity);
				}
			}

			// creating customer's account
			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setUserId(userEntity.getId());
			customerEntity.setUserByUserId(userEntity);
			if (newDevice != null) {
				customerEntity.setDeviceId(newDevice.getId());
				customerEntity.setDeviceByDeviceId(newDevice);
			}
			customerEntity.setSignUpDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			customerEntity.setPremium(false);
			customerEntity.setNotifyReceiver(true);
			customerEntity.setCustomerId(
					StringUtils.isEmpty(signUpRequest.getCustomerId()) ? generateCustomerSequence(userEntity.getId())
							: signUpRequest.getCustomerId());
			customerEntity.setReferralCode(createRandomCode(6));
			customerEntity.setWebSignUp(signUpRequest.isWebSignUp());
			customerEntity.setCreatedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			customerEntity.setModifiedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			customerEntity.setCreatedBy(userEntity.getId());
			customerEntity.setModifiedBy(userEntity.getId());

			//sendWelcomeEmail(userEntity, customerEntity.getReferralCode());
			return customerEntityRepository.save(customerEntity);
		}
		throw new Exception();
	}
	
	private String generateCustomerSequence(Integer userId) {
		return "CUS" + userId + DateTime.now().getDayOfYear();
	}
	
	private String createRandomCode(int codeLength) {
		return RandomStringUtils.randomAlphanumeric(codeLength);
	}
	
	public boolean changePassword(ChangePasswordRequest changePasswordRequest) throws BusinessException {
		if (!StringUtils.isEmpty(changePasswordRequest.getUsername())) {
			Optional<UserEntity> userEntityOptional = userRepository.findByEmail(changePasswordRequest.getUsername());
			if (!userEntityOptional.isPresent()) {
				userEntityOptional = userRepository.findByPhone(Long.valueOf(changePasswordRequest.getUsername()));
			}
			if (userEntityOptional.isPresent()) {
				UserEntity userEntity = userEntityOptional.get();
				if (matchPassword(changePasswordRequest.getOldPassword(), userEntity.getPassword())) {
					userEntity.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
					userRepository.save(userEntity);
					return true;
				} else {
					throw new BusinessException(BusinessExceptionCode.PASSWORD_DOES_NOT_MATCH);
				}
			} else {
				throw new BusinessException(BusinessExceptionCode.EMAIL_NOT_FOUND);
			}
		} else {
			Optional<UserEntity> optionalEntity = userRepository
					.findByPhone(Long.valueOf(changePasswordRequest.getUsername()));
			if (optionalEntity.isPresent()) {
				UserEntity userEntity = optionalEntity.get();
				if (matchPassword(changePasswordRequest.getOldPassword(), userEntity.getPassword())) {
					userEntity.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
					userRepository.save(userEntity);
					return true;
				} else {
					throw new BusinessException(BusinessExceptionCode.PASSWORD_DOES_NOT_MATCH);
				}
			} else {
				throw new BusinessException(BusinessExceptionCode.INVALID_PHONE_NUMBER);
			}
		}
	}
	
	private boolean matchPassword(String oldPassword, String encodedPassword) throws BusinessException {
		if (passwordEncoder.matches(oldPassword, encodedPassword)) {
			return true;
		}
		throw new BusinessException(BusinessExceptionCode.PASSWORD_DOES_NOT_MATCH);
	}
}
