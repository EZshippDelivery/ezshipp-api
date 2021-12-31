package com.ezshipp.api.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ezshipp.api.enums.UserType;
import com.ezshipp.api.exception.ServiceException;
import com.ezshipp.api.model.PushResponse;
import com.ezshipp.api.notification.FCMNotification;
import com.ezshipp.api.persistence.entity.CustomerEntity;
import com.ezshipp.api.persistence.entity.PushEventEntity;
import com.ezshipp.api.repository.PushEventRepository;

@Service
public class PushNotificationService {

	 private static final String STORE_LAUNCH_TITLE= "Ezshipp Store";
	    private static final String STORE_LAUNCH_MESSAGE = "Ezshipp store is now available for shopping. Please check out on the app.";
	    private static final String UPDATE_APP_TITLE = "Update App";
	    private static final String UPDATE_APP_MESSAGE = "Please update the latest app to avail this new features.";
	    private static final String OFFER_CODE_TITLE = "Happy Independence";
	    private static final String OFFER_CODE_MESSAGE = "We wish to be a part of your Independence day celebrations. Get your deliveries done from Ezshipp by getting a 20% discount using our coupon code IND20 on 15th August";

	    private final CustomerEntityService customerEntityService;


	    private final FCMNotification fcmNotification;

	    private final PushEventRepository pushEventRepository;
	    
	    public PushNotificationService(CustomerEntityService customerEntityService, FCMNotification fcmNotification,
                PushEventRepository pushEventRepository) {
this.customerEntityService = customerEntityService;
this.fcmNotification = fcmNotification;
this.pushEventRepository = pushEventRepository;
}
	    
	    public void notify(String notifyMessage, String notifyTitle) throws ServiceException, JSONException {
	        List<CustomerEntity> customerEntities = customerEntityService.getAllNonPremiumCustomers();
	        for (CustomerEntity entity : customerEntities) {
	            pushMessage(entity, notifyMessage, notifyTitle);
	        }
	    }

	    /**
	     * push message to the customer app on status updates
	     *
	     * @param customerEntity customer entity
	     * @throws ServiceException the service exception
	     * @throws JSONException the json exeption
	     */
	    private void pushMessage(CustomerEntity customerEntity, String pushMessage, String pushTitle) throws ServiceException, JSONException {
	        if (!customerEntity.isWebSignUp()  && !customerEntity.isPremium() && customerEntity.getDeviceByDeviceId() != null)   {
	            String message = buildFCMMessage(customerEntity.getDeviceByDeviceId().getDeviceToken(), pushMessage, pushTitle);
	            if (message != null) {
	                PushResponse pushResponse = fcmNotification.pushFCMNotification(message, customerEntity.getDeviceByDeviceId().getDeviceType());
	               // log.info("message has been pushed to customer for store notification: " + customerEntity.getId());
	                PushEventEntity pushEventEntity = new PushEventEntity();
	                pushEventEntity.setEventId(customerEntity.getId());
	                pushEventEntity.setFailure(pushResponse.getFailure() == 1);
	                pushEventEntity.setSuccess(pushResponse.getSuccess() == 1);
	                pushEventEntity.setMultiCastId(pushResponse.getMultiCastId());
	                pushEventEntity.setResult(pushResponse.results());
	                pushEventEntity.setType(UserType.CUSTOMER.name().toLowerCase());
	                pushEventRepository.save(pushEventEntity);
	            }
	        }
	    }

	    private String buildFCMMessage(String deviceToken, String notifyMessage, String notifyTitle) throws JSONException   {
	        JSONObject json = new JSONObject();
	        if (!StringUtils.isEmpty(deviceToken)) {
	            json.put("to", deviceToken.trim());

	            JSONObject message = new JSONObject();
	            JSONObject notification = new JSONObject();
	            notification.put("title", notifyTitle); // Notification title
	            notification.put("body", notifyMessage); // Notification title
	            message.put("title", "ezshipp"); // Notification title
	            message.put("body", "EZSHIPP STORE LAUNCHED"); // Notification body
	            message.put("status", "NEW");
	            message.put("message", notifyMessage);
	            message.put("orderId", 1);
	            message.put("orderSeqId", "E00001234");
	            message.put("type", "NOTIFICATION");
	            message.put("statusId", 1);

	            json.put("data", message);
	            json.put("notification", notification);
	            json.put("priority", "high");
	            json.put("collapse_key", "your_collapse_key");
	            return json.toString();
	        }

	        return null;
	    }

}
