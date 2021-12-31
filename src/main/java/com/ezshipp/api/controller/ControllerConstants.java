package com.ezshipp.api.controller;

/**
 * The Interface ControllerConstants.
 */
public interface ControllerConstants {

    /** The Constant AUTOUPGRADE_BUSINESS_EXCEPTION_MESSAGE. */
    String AUTOUPGRADE_BUSINESS_EXCEPTION_MESSAGE = "Business Exception - A data problem with Confirmation Number or InnNumber or Room Type";

    /** The Constant AVAILABILITY_BUSINESS_EXCEPTION_MESSAGE. */
    String AVAILABILITY_BUSINESS_EXCEPTION_MESSAGE = "Business Exception - A data problem with InnNumber or ArrivalDate or DepartureDate";

    /** The Constant BUSINESS_EXCEPTION_CODE. */
    int BUSINESS_EXCEPTION_CODE = 422;

    /** The Constant BUSINESS_EXCEPTION_MESSAGE. */
    String BUSINESS_EXCEPTION_MESSAGE = "Business Exception - A data problem";

    /** The Constant BUSINESS_EXCEPTION_MESSAGE. */
    String BUSINESS_EXCEPTION_MESSAGE_PROPERTY_EXCLUSION = "Business Exception - A data problem (start date, end date, or inn number is invalid)";

    /** The Constant COMMA. */
    String COMMA = ",";

    /** The Constant EZSHIPP_AUTH_KEY. */
    String EZSHIPP_AUTH_KEY = "EZSHIPP_AUTH_KEY";

    /** The Constant EZSHIPP_AUTH_NEW_PASSWORD. */
    String EZSHIPP_AUTH_NEW_PASSWORD = "EZSHIPP_AUTH_NEW_PASSWORD";

    /** The Constant EZSHIPP_AUTH_PASSWORD. */
    String EZSHIPP_AUTH_PASSWORD = "EZSHIPP_AUTH_PASSWORD";

    /** The Constant PARTIAL_SUCCESS_BUSINESS_EXCEPTION_CODE. */
    int PARTIAL_SUCCESS_BUSINESS_EXCEPTION_CODE = 427;

    /** The Constant PARTIAL_SUCCESS_BUSINESS_EXCEPTION_MESSAGE. */
    String PARTIAL_SUCCESS_BUSINESS_EXCEPTION_MESSAGE = "Partial Success Business Exception - A data problem with partial success";

    /** The Constant SERVICE_EXCEPTION_CODE. */
    int SERVICE_EXCEPTION_CODE = 567;

    /** The Constant SERVICE_EXCEPTION_MESSAGE. */
    String SERVICE_EXCEPTION_MESSAGE = "Service Exception - An internal problem that the user cannot resolve";

    /** The Constant SUCCESS_CODE. */
    int SUCCESS_CODE = 200;

    /** The Constant SUCCESS_MESSAGE. */
    String SUCCESS_MESSAGE = "Success";

    /** The Constant US. */
    String US = "US";

}
