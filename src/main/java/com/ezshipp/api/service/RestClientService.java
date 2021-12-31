//package com.ezshipp.api.service;
//
//import com.ezshipp.api.config.ApplicationPropertyConfig;
//import com.ezshipp.api.exception.ServiceException;
//import com.ezshipp.api.exception.ServiceExceptionCode;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//import org.springframework.stereotype.Service;
//
//import javax.inject.Inject;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//@Service
//@Slf4j
//public class RestClientService {
//    private static final String JSON = "json";
//    private static final String OTP = "otp";
//
//    private final ApplicationPropertyConfig applicationPropertyConfig;
//
//    @Inject
//    public RestClientService(ApplicationPropertyConfig applicationPropertyConfig) {
//        this.applicationPropertyConfig = applicationPropertyConfig;
//    }
//
//    public String postSMSRequest(String body) throws ServiceException {
//        try {
//            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
//            String  message=URLEncoder.encode(body, "UTF-8");
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(applicationPropertyConfig.getSolutionsInfiniUrl() + "?api_key=" + applicationPropertyConfig.getSolutionsInfiniApiKey() + "&method=sms.json&json="+message)
//                    .post(requestBody)
//                    .addHeader("X-Auth-Key", applicationPropertyConfig.getSolutionsInfiniApiKey())
//                    .addHeader("X-Api-Method", OTP)
//                    .addHeader("X-Api-Format", JSON)
//                    .build();
//        	Response response = client.newCall(request).execute();
//            String responseBody = response.body().string();
//            log.info(responseBody);
//            response.close();
//            return responseBody;
//        } catch (UnsupportedEncodingException e) {
//            log.error("Exception in sending SMS", e);
//            throw new ServiceException(ServiceExceptionCode.SMS_SEND_FAILED, e);
//        } catch (IOException e) {
//            log.error("Exception in sending SMS", e);
//            throw new ServiceException(ServiceExceptionCode.SMS_SEND_FAILED, e);
//        }
//    }
//
//    public String getIPStack(String ipAddress) throws ServiceException {
//        try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(applicationPropertyConfig.getIpstackApiUrl() + "/" + ipAddress + "?access_key=" + applicationPropertyConfig.getIpstackAccessKey())
//                    .get()
//                    .build();
//            Response response = client.newCall(request).execute();
//            String responseBody = response.body().string();
//            log.info(responseBody);
//            response.close();
//            return responseBody;
//        } catch (UnsupportedEncodingException e) {
//            log.error("Exception in getting Country info by IP Address", e);
//            throw new ServiceException(ServiceExceptionCode.GET_COUNTRY_BY_IP_FAILED, e);
//        } catch (IOException e) {
//            log.error("Exception in getting Country info by IP Address", e);
//            throw new ServiceException(ServiceExceptionCode.GET_COUNTRY_BY_IP_FAILED, e);
//        }
//    }
//
//    public void postUpdatesToCentral(String externalOrderId) {
//        //http://magento.quad1test.com/magentogst/gst/api/rest/pace/ez/id/{orderId}
//        try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url("http://magento.quad1test.com/magentogst/gst/api/rest/pace/ez/id/" + externalOrderId)
//                    .get()
//                    .build();
//            Response response = client.newCall(request).execute();
//            String responseBody = response.body().string();
//            log.info(responseBody);
//            response.close();
//        } catch (UnsupportedEncodingException e) {
//            log.error("Exception in updating Central", e);
//            //throw new ServiceException(ServiceExceptionCode.ORDER_U, e);
//        } catch (IOException e) {
//            log.error("Exception in updating Central", e);
//            //throw new ServiceException(ServiceExceptionCode.SMS_SEND_FAILED, e);
//        }
//    }
//}
