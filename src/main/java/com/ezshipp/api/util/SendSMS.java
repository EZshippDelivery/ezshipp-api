package com.ezshipp.api.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class SendSMS {

	public String sendSms(String phoneNumner, String otp) {
		try {
			// Construct data
			String apiKey = "apikey=" + "NzI3NTc1NDQ1NTc5Mzk1NjQzNjY2MjcwNjE1MTMxN2E=";
			String message = "&message=" + "One Time Password for ezshipp is " +otp;
			String sender = "&sender=" + "Ezship";
			String numbers = "&numbers=" + "+91"+phoneNumner;
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
}
