//package com.ezshipp.api.service;
//
//import static com.ezshipp.api.model.ApplicationGlobalConstants.*;
//
//import javax.inject.Inject;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//
//import com.ezshipp.api.exception.ServiceException;
//import com.ezshipp.api.model.SMSRequest;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Created by srinivasseri on 2019-08-27.
// */
//
//@Service
//@Slf4j
//public class SMSService {
//
//    private final RestClientService restClientService;
//
//    @Inject
//    public SMSService(RestClientService restClientService) {
//        this.restClientService = restClientService;
//    }
//
//    public void sendSMS(SMSRequest smsRequest)   {
//        try {
//            String response = restClientService.postSMSRequest(buildSMSData(smsRequest));
//            log.info("sent SMS to  " + response);
//        } catch (ServiceException e) {
//            log.info("exception raised in sending SMS to receiver: ", e);
//        }
//    }
//
//    private String buildSMSData(SMSRequest smsRequest) {
//        JSONObject json = new JSONObject();
//        json.put(SENDER, SENDER_EZSHIPP);
//
//        String message = "Dear " + smsRequest.getCustomerName() + ",";
//        message = message + "\n" + smsRequest.getMessage();
//        message = message + ", " + "if you have any questions, please call us at 040-49674477, thank you Ezshipp";
//        log.info(message);
//        JSONArray array = new JSONArray();
//        JSONObject item = new JSONObject();
//        item.put(TO, NINTYONE + smsRequest.getPhone());
//        item.put(MESSAGE, message);
//        array.put(item);
//        json.put(SMS, array);
//
//        return json.toString();
//    }
//
//}
