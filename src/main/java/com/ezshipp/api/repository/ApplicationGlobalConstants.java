package com.ezshipp.api.repository;

import org.springframework.data.domain.Sort;

public interface ApplicationGlobalConstants {

    int DEFAULT_PAGE_NUMBER = 1;
    int DEFAULT_PAGE_SIZE = 10;
    int DEFAULT_SORT_DIRECTION = Sort.Direction.DESC.ordinal();
    String[] DEFAULT_SORT_FIELDS = new String[]{"lastModifiedDate"};

    static double AYYAPA_HUB_LATITUDE = 17.45516;
    static double AYYAPA_HUB_LONGITUDE = 78.388618;

    static double BIRLA_SCHOOL_LATITUDE = 17.4563328;
    static double BIRLA_SCHOOL_LONGITUDE = 78.372864;
    static Integer BIRLA_SCHOOL_ADDRESS_ID = 10456;

    static final String SENDER = "sender";
    static final String SENDER_EZSHIPP = "EZSHIP";
    static final String TO = "to";
    static final String SMS = "sms";
    static final String MESSAGE = "message";
    static final String STATUS = "status";
    static final String OK = "OK";
    static final String DATA = "data";
    static final String MOBILE = "mobile";
    static final String NINTYONE = "91";
    static final String CUT_OFF_MESSAGE = "Since you are placing an order after our cut off time, Your parcel will be picked and delivered on next Business Day.";

    double SGST_TAX_PERCENTAGE = 0.09;
    double CGST_TAX_PERCENTAGE = 0.09;
    int INVOICE_DUE_DATE_DAYS = 10;
    int DEFAULT_DRIVER_ID = 5;
    int CENTRAL_BOOKS_CUSTOMER = 2;
    int SYSTEM_USER_ID = 156;

    static final String STORE_LAUNCH_TITLE= "Ezshipp Store";
    static final String STORE_LAUNCH_MESSAGE = "Ezshipp store is now available for shopping. Please check out on the app.";
    static final String UPDATE_APP_TITLE = "Update App";
    static final String UPDATE_APP_MESSAGE = "Please update the latest app to avail this new features.";
    static final String OFFER_CODE_TITLE = "Happy Independence";
    static final String OFFER_CODE_MESSAGE = "We wish to be a part of your Independence and Rakhi celebrations. Get your deliveries done from Ezshipp by getting a 20% discount using our coupon code IND20 for three days";
}
