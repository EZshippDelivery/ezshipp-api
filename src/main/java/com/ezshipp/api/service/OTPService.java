package com.ezshipp.api.service;



import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezshipp.api.enums.AuthType;
import com.ezshipp.api.enums.EMessageType;
import com.ezshipp.api.exception.BusinessException;
import com.ezshipp.api.exception.BusinessExceptionCode;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.Delivery;
import com.ezshipp.api.model.OTPRequest;
import com.ezshipp.api.model.OTPResponse;
import com.ezshipp.api.model.OTPVerifyResponse;
import com.ezshipp.api.model.Otp;
import com.ezshipp.api.model.ProfileResponse;
import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.persistence.entity.OtpNumberEntity;
import com.ezshipp.api.repository.CustomerEntityRepository;
import com.ezshipp.api.repository.OtpNumberEntityRepository;
import com.ezshipp.api.util.OTPUtil;
import com.ezshipp.api.util.SendSMS;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OTPService {

	@Autowired
	CustomerEntityRepository customerEntityRepository;
	
	@Autowired
	OtpNumberEntityService otpNumberEntityService;
	
	@Autowired
	OtpNumberEntityRepository otpNumberEntityRepository;
	
	@Autowired
	SendSMS sendSMS;
	
	@Autowired
	CustomerEntityService customerEntityService;
	
	private static final String NINTYONE = "91";
	 public OTPResponse sendOTP(OTPRequest otpRequest) throws ServiceException, JSONException, BusinessException {
	        Long oneTimePassCode = OTPUtil.generateOTP();
	        
	         Optional<CustomerEntity> customer = customerEntityRepository.findById(otpRequest.getCustomerId());
	         System.out.println(customer.get());
	         System.out.println(customer.get().getUserByUserId().getPhone());
	        if(customer.isPresent()) {
	        AuthType authType = AuthType.valueOf(otpRequest.getAuthType());
	        ObjectMapper mapper = new ObjectMapper();
	        if (authType == AuthType.EMAIL) {
	            Otp otp = new Otp();
	            otp.setEMessageType(EMessageType.CUSTOMER_OTP);
	            otp.setOtpCode(String.valueOf(oneTimePassCode));
	            //otp.setRecipients(Arrays.asList(customerEntity.getUserByUserId().getEmail()));
//	            try {
//	                amazonSNSPublisherService.publishEmail(mapper.writeValueAsString(otp), EMessageType.CUSTOMER_OTP);
//	            } catch (ServiceException | JsonProcessingException e) {
//	                log.error("unable to send OTP by email", e);
//	            }
	        } else  if(authType == AuthType.SMS){
	            try {
	            	
	            	sendSMS.sendSms(mapper.writeValueAsString(customer.get().getUserByUserId().getPhone()),oneTimePassCode.toString());
	            	//otpNumberEntityService.deleteByContact(otpRequest.getPhone());
                    OtpNumberEntity otpNumberEntity = new OtpNumberEntity();
                    otpNumberEntity.setContact(otpRequest.getPhone());
                    otpNumberEntity.setVerificationCode(String.valueOf(oneTimePassCode));
                    otpNumberEntity.setVerified(false);
                    otpNumberEntity.setSentDate(new Timestamp(System.currentTimeMillis()));
                    otpNumberEntity.setApiMessage("");
                    otpNumberEntityService.saveOrUpdateOtp(otpNumberEntity);
                    //otpNumberEntityRepository.save(otpNumberEntity);
//	                NotificationRequest notificationRequest = new NotificationRequest();
//	                notificationRequest.setLanguage(new Language("en"));
//	                Map<String, Object> data = new HashMap<>();
//	                data.put("otp", String.valueOf(oneTimePassCode));
//	                notificationRequest.setData(data);
//	                notificationRequest.setMethod(ENotificationMethod.SMS);
//	                notificationRequest.setSource(ENotificationSource.OTP);
//	                notificationRequest.setRecipients(Arrays.asList(NINTYONE + customerEntity.getUserByUserId().getPhone()));
//	                notificationRequest.setTemplate(SmsTemplateType.OTP.getTemplate());
//	                amazonSNSPublisherService.publishSMS(mapper.writeValueAsString(notificationRequest), EMessageType.CUSTOMER_OTP);
	            } catch (Exception e) {
	                //log.error("unable to send OTP", e);
	            }
	        }
//	        if (authType == AuthType.EMAIL) {
//	            emailService.sendEmail(buildEmailData(otpRequest, oneTimePassCode), null, null);
//	            String id = "email sent";
//	            otpNumberEntityService.deleteByContact(otpRequest.getEmail());
//	            OtpNumberEntity otpNumberEntity = new OtpNumberEntity();
//	            otpNumberEntity.setContact(otpRequest.getEmail());
//	            otpNumberEntity.setVerificationCode(String.valueOf(oneTimePassCode));
//	            otpNumberEntity.setVerified(false);
//	            otpNumberEntity.setSentDate(new Timestamp(System.currentTimeMillis()));
//	            otpNumberEntity.setApiMessage(id);
//	            otpNumberEntityRepository.save(otpNumberEntity);
//	        } 
//	        else {
//	            String response = restClientService.postSMSRequest(buildSMSData(otpRequest, oneTimePassCode));
//	            if (!StringUtils.isEmpty(response)) {
//	                JSONObject result = new JSONObject(response);
//	                if (result.getString(STATUS).equalsIgnoreCase(OK)) {
//	                    String mobile = result.getJSONArray(DATA).getJSONObject(0).getString(MOBILE);
//	                    if (mobile.equalsIgnoreCase(otpRequest.getPhone())) {
//	                        otpNumberEntityService.deleteByContact(otpRequest.getPhone());
//	                        OtpNumberEntity otpNumberEntity = new OtpNumberEntity();
//	                        otpNumberEntity.setContact(otpRequest.getPhone());
//	                        otpNumberEntity.setVerificationCode(String.valueOf(oneTimePassCode));
//	                        otpNumberEntity.setVerified(false);
//	                        otpNumberEntity.setSentDate(new Timestamp(System.currentTimeMillis()));
//	                        otpNumberEntity.setApiMessage(result.getJSONArray(DATA).toString());
//	                        otpNumberEntityRepository.save(otpNumberEntity);
//	                    }
//	                }
//	            }
//	        }

	        return buildOtpResponse(otpRequest, oneTimePassCode);
	    }
	        else {
	        	throw new BusinessException(BusinessExceptionCode.CUSTOMER_NOT_FOUND);
	        }
	 }
	 
	 private OTPResponse buildOtpResponse(OTPRequest otpRequest, long otp) {
	        OTPResponse otpResponse = new OTPResponse();
	        otpResponse.setOtp(String.valueOf(otp));
	        otpResponse.setAuthType(otpRequest.getAuthType());
	        Delivery delivery = new Delivery();
	        delivery.setSendStatus("SUCCESS");
	        delivery.setSendTime(new Date());
	        delivery.setContact(otpRequest.getAuthType().equalsIgnoreCase("SMS") ? otpRequest.getPhone() : otpRequest.getEmail());
	        otpResponse.setDelivery(delivery);
	        return otpResponse;
	    }
	 
	 public OTPVerifyResponse verifyOTP(String contact, String otp) throws BusinessException, ServiceException {
	        OtpNumberEntity otpNumberEntity = otpNumberEntityService.findByContact(contact, false);
	        boolean status = false;
	        if (otpNumberEntity == null) {
	            throw new BusinessException(BusinessExceptionCode.INVALID_PHONE_OR_EMAIL);
	        }

	        OTPVerifyResponse otpVerifyResponse = new OTPVerifyResponse();
	        if (otpNumberEntity.getVerificationCode().equalsIgnoreCase(otp)) {
	            otpVerifyResponse.setOtpVerified(true);
	            otpNumberEntity.setVerified(true);
	            otpNumberEntityRepository.save(otpNumberEntity);
	        }
	        return otpVerifyResponse;
	    }
}
