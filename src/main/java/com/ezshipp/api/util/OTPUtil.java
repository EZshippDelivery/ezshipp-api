package com.ezshipp.api.util;

import java.util.Random;

public class OTPUtil {
    private static final Random generator = new Random();

    public static long generateOTP() {
        return 100000 + generator.nextInt(900000);
    }

    public static void main(String[] args) {
        for (int i=0;i<200;i++) {
            System.out.println(generateOTP());
        }
    }
}
