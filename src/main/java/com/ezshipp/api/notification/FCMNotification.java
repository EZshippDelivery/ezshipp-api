package com.ezshipp.api.notification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.ezshipp.api.enums.DeviceTypeEnum;
import com.ezshipp.api.model.PushResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class FCMNotification {

	 private final static String AUTH_KEY_FCM = "AAAATftpntc:APA91bGUg0AJxmHUqI_0mS4Itm5G2fvtTOvrevJZeSBfFal896PRlSd4T2mIvxJYrNyzY6OE7oEMKuHWraz3Ov4UQS_KgIo8E7zomZr9cLyk59ypOINGWQanoC0s5CYR3DHSx4G2BNJC";
	    private final static String AUTH_KEY_FCM_IOS = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgfb+2wZ7se3DSX/9MS1FMzg+hhBWUIvYXSWpPOzRXhfigCgYIKoZIzj0DAQehRANCAARkykk+SvHpy87x5gj7TQ37UE02aacTeG0pgUCduy1NjtrIxoR9NO15P/gpqBHnTHcK679kwIIuZlRO2uL52J0F";
	    private final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	    public PushResponse pushFCMNotification(String message, DeviceTypeEnum osType) {
	        PushResponse pushResponse = null;
	        try {
	            URL url = new URL(API_URL_FCM);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            conn.setUseCaches(false);
	            conn.setDoInput(true);
	            conn.setDoOutput(true);

	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Authorization", "key=" +  AUTH_KEY_FCM);
	            conn.setRequestProperty("Content-Type", "application/json");

	            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	            wr.write(message);
	            wr.flush();
	            wr.close();

	            int responseCode = conn.getResponseCode();
	          //  log.info("Response Code : " + responseCode);

	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	          //  log.info("Response : " + response);

	            ObjectMapper objectMapper = new ObjectMapper();
	            pushResponse = objectMapper.readValue(response.toString(), PushResponse.class);
	            in.close();
	        } catch (Exception e)   {
	         //   log.error("failed to push the notification:["+ message + "]", e);
	          //  throw new ServiceException(ServiceExceptionCode.PUSH_NOTIFICATION_FAILED);
	        }

	        return pushResponse;
	    }
}
