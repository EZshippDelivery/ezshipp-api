package com.ezshipp.api.exception;

/**
 * The Enum BusinessExceptionCode provides a code for all business exceptions so that we can map them correctly in the resource bundle.
 */
public enum BusinessExceptionCode {

    /** user already exists error. */
    USER_ALREADY_EXISTS("100"),

    /** user already exists error. */
    PHONE_ALREADY_EXISTS("103"),

    /** email already exists error. */
    EMAIL_ALREADY_EXISTS("101"),

    /** email not found. */
    EMAIL_NOT_FOUND("108"),

    /** email already exists error. */
    INVALID_PAGE_SIZE_NUMBER("104"),

    INVALID_USERNAME_OR_PASSWORD("115"),

    /** Customer not found. */
    CUSTOMER_NOT_FOUND("202"),

    /** tracking not available. */
    TRACKING_NOT_AVAILABLE("212"),

    /** Biker not found. */
    BIKER_NOT_FOUND("302"),
    BIKER_COUNT_NOT_FOUND("322"),
    ORDER_ALREADY_DELIVERED("317"),

    /** leave request not found. */
    LEAVE_REQUEST_NOT_FOUND("303"),

    /** Leave status invalid. */
    INVALID_LEAVE_STATUS("304"),

    ORDER_STATUS_ALREADY_EXISTS("404"),

    /** Order not found. */
    ORDER_NOT_FOUND("402"),

    EMAIL_OR_PHONE_REQUIRED("102"),

    /** User password does not match. */
    PASSWORD_DOES_NOT_MATCH("107"),

    /** sequence id not found. */
    COUNTER_SEQ_ID_NOT_FOUND("5001"),

    /** zone not found. */
    ZONE_NOT_FOUND("201"),

    /** invalid order id. */
    INVALID_ORDER_ID("301"),
    INVALID_CUSTOMER_ID("324"),

    /** invalid order status id. */
    INVALID_ORDER_STATUS_ID("305"),

    ZONE_ID_REQUIRED("306"),

    BARCODE_ID_REQUIRED("307"),

    BIKER_COMMENTS_REQUIRED("308"),

    DELIVERED_AT_REQUIRED("309"),

    SIGN_URL_REQUIRED("310"),

    NEW_DRIVER_ID_REQUIRED("314"),

    BARCODE_ID_DOES_NOT_EXIST("313"),

    CANCEL_REASON_REQUIRED("316"),

    INVALID_DRIVER_ID("315"),

    INVALID_OLD_DRIVER_ID("320"),

    INVALID_NEW_DRIVER_ID("321"),

    /** The invalid phone number. */
    INVALID_PHONE_NUMBER("204"),

    INVALID_EMAIL("206"),

    INVALID_PHONE_OR_EMAIL("205"),

    /** The OTP does not exist in the system. */
    OTP_DOES_NOT_EXIST("208"),

    /** The account locked by agent. */
    ACCOUNT_LOCKED_BY_AGENT("288"),

    /** The adult number must be positive. */
    ADULT_NUMBER_MUST_BE_POSITIVE("144"),

    /** The amount after tax not found. */
    AMOUNT_AFTER_TAX_NOT_FOUND("141"),

    /** The arrival date after credit card expiration date. */
    ARRIVAL_DATE_AFTER_CREDIT_CARD_EXPIRATION_DATE("171"),

    /** The arrival date is equals or after to departure date. */
    ARRIVAL_DATE_EQUALS_OR_AFTER_DEPARTURE_DATE("173"),

    /** The arrival date too early. */
    ARRIVAL_DATE_TOO_EARLY("149"),

    /** The arrival date too late. */
    ARRIVAL_DATE_TOO_LATE("180"),

    /** The arrivals file invalid xml format. */
    ARRIVALS_FILE_INVALID_XML_FORMAT("1505"),

    /** Authentication failed on security token. */
    AUTHENTICATION_FAILED_SECURITY_TOKEN("203"),

    /** The autoupgrade request invalid xml format. */
    AUTOUPGRADE_ASSIGNMENT_REQUEST_INVALID_XML_FORMAT("6109"),

    /** The autoupgrade response invalid xml format. */
    AUTOUPGRADE_ASSIGNMENT_RESPONSE_INVALID_XML_FORMAT("6108"),

    /** The batch checksum cannot be null. */
    BATCH_CHECKSUM_CANNOT_BE_NULL("1056"),

    /** The batch file cannot be directory. */
    BATCH_FILE_CANNOT_BE_DIRECTORY("1051"),

    /** The batch file checksum mismatch. */
    BATCH_FILE_CHECKSUM_MISMATCH("1053"),

    /** The batch file invalid xml format. */
    BATCH_FILE_INVALID_XML_FORMAT("1062"),


    /** The batch file not exists. */
    BATCH_FILE_NOT_EXISTS("1050"),

    /** The batch file is not in gzipped. */
    BATCH_FILE_NOT_GZIPPED("1114"),

    /** The batch file uri cannot be null. */
    BATCH_FILE_URI_CANNOT_BE_NULL("1055"),

    /** The can not delete default descriptions. */
    CAN_NOT_DELETED_DEFAULT_DESCRIPTION("120"),

    /** The ccs insuffecient permissions. */
    CCS_INSUFFECIENT_PERMISSIONS("2043"),

    /** The ccs invalid agent email. */
    CCS_INVALID_AGENT_EMAIL("2025"),

    /** The ccs invalid agent email for role. */
    CCS_INVALID_AGENT_EMAIL_FOR_ROLE("2024"),

    /** The ccs invalid agent email password combo on post. */
    CCS_INVALID_AGENT_EMAIL_PASSWORD_COMBO_ON_POST("2029"),

    /** The ccs invalId agent id. */
    CCS_INVALID_AGENT_ID("2009"),

    /** The ccs invalid agent id on post. */
    CCS_INVALID_AGENT_ID_ON_POST("2026"),

    /** The ccs invalid agent location. */
    CCS_INVALID_AGENT_LOCATION("2023"),

    /** The ccs invalid agent name. */
    CCS_INVALID_AGENT_NAME("2019"),

    /** The ccs invalid agent on post. */
    CCS_INVALID_AGENT_ON_POST("2028"),

    /** The ccs invalid agent password. */
    CCS_INVALID_AGENT_PASSWORD("2045"),

    /** The ccs invalid agent password for role. */
    CCS_INVALID_AGENT_PASSWORD_FOR_ROLE("2021"),

    /** The ccs invalid agent password format. */
    CCS_INVALID_AGENT_PASSWORD_FORMAT("2033"),

    /** The ccs invalid agent role. */
    CCS_INVALID_AGENT_ROLE("2022"),

    /** The ccs invalid agent role for password reset. */
    CCS_INVALID_AGENT_ROLE_FOR_PASSWORD_RESET("2032"),

    /** The ccs invalid agent role for password update. */
    CCS_INVALID_AGENT_ROLE_FOR_PASSWORD_UPDATE("2036"),

    /** The ccs invalid agent ccadmin location. */
    CCS_INVALID_AGENT_ROLE_LOCATION("2020"),

    /** The ccs invalid agent status filter. */
    CCS_INVALID_AGENT_STATUS_FILTER("2046"),

    /** The ccs invalid agent status on post. */
    CCS_INVALID_AGENT_STATUS_ON_POST("2027"),

    /** The ccs invalid agent status on put. */
    CCS_INVALID_AGENT_STATUS_ON_PUT("2044"),

    /** The ccs invalid employee account. */
    CCS_INVALID_EMPLOYEE_ACCOUNT("2042"),

    /** The ccs invalid employee returns number. */
    CCS_INVALID_EMPLOYEE_RETURNS_NUMBER("2037"),

    /** The ccs invalid employee returns password. */
    CCS_INVALID_EMPLOYEE_RETURNS_PASSWORD("2038"),

    /** The ccs invalid fnc date order. */
    CCS_INVALID_FNC_DATE_ORDER("2010"),

    /** The ccs invalid fnc date timespan. */
    CCS_INVALID_FNC_DATE_TIMESPAN("2014"),

    /** The ccs invalid fnc future arrival date. */
    CCS_INVALID_FNC_FUTURE_ARRIVAL_DATE("2013"),

    /** The ccs invalid fnc hotel tier. */
    CCS_INVALID_FNC_HOTEL_TIER("2015"),

    /** The ccs invalid fnc number of certificates. */
    CCS_INVALID_FNC_NUMBER_OF_CERTIFICATES("2012"),

    /** The ccs invalid fnc past arrival date. */
    CCS_INVALID_FNC_PAST_ARRIVAL_DATE("2011"),

    /** The ccs invalid military rewards id. */
    CCS_INVALID_MILITARY_REWARDS_ID("2016"),

    /** The ccs invalid nonemployee returns number. */
    CCS_INVALID_NONEMPLOYEE_RETURNS_NUMBER("2039"),

    /** The ccs invalid nonemployee returns password. */
    CCS_INVALID_NONEMPLOYEE_RETURNS_PASSWORD("2040"),

    /** The invalid or expired token. */
    CCS_INVALID_OR_EXPIRED_TOKEN("2034"),

    /** The invalid password reset. */
    CCS_INVALID_PASSWORD_RESET("2035"),

    /** The ccs invalid returns enrollment channel. */
    CCS_INVALID_RETURNS_ENROLLMENT_CHANNEL("2047"),

    /** The ccs invalid returns enrollment method. */
    CCS_INVALID_RETURNS_ENROLLMENT_METHOD("2048"),

    /** The ccs invalid email. */
    CCS_INVALID_SEARCH_EMAIL("2006"),

    /** The ccs invalid search enrollment status. */
    CCS_INVALID_SEARCH_ENROLLMENT_STATUS("2008"),

    /** The ccs invalid first name. */
    CCS_INVALID_SEARCH_FIRST_NAME("2001"),

    /** The ccs invalid last name. */
    CCS_INVALID_SEARCH_LAST_NAME("2002"),

    /** The ccs invalid search phone. */
    CCS_INVALID_SEARCH_PHONE("2007"),

    /** The ccs invalid input postal. */
    CCS_INVALID_SEARCH_POSTAL("2004"),

    /** The ccs invalid returns number id. */
    CCS_INVALID_SEARCH_RETURNS_NUMBER("2003"),

    /** The ccs invalid input street. */
    CCS_INVALID_SEARCH_STREET("2005"),

    /** The ccs missing agent email. */
    CCS_MISSING_AGENT_EMAIL("2031"),

    /** The ccs missing agent role for id. */
    CCS_MISSING_AGENT_ROLE_FOR_ID("2018"),

    /**
     * The ccs missing agent email. / CCS_MISSING_AGENT_EMAIL("2031"),
     *
     * /* The ccs missing agent role for id. / CCS_MISSING_AGENT_ROLE_FOR_ID("2018"),
     *
     * /* The ccs missing password on post. / CCS_MISSING_PASSWORD_ON_POST("2030"),
     *
     * /* The ccs missing returns.
     */
    CCS_MISSING_RETURNS("2041"),

    /** The ccs unable to generate id. */
    CCS_UNABLE_TO_GENERATE_ID("2017"),

    /** The checkout confirmation number not found. */
    CHECKOUT_CONFIRMATION_NUMBER_NOT_FOUND("517"),

    /** The checkout request invalid xml format. */
    CHECKOUT_REQUEST_INVALID_XML_FORMAT("512"),

    /** The checkout response invalid xml format. */
    CHECKOUT_RESPONSE_INVALID_XML_FORMAT("513"),

    /** The checkout returns number not found. */
    CHECKOUT_RETURNSNUMBER_NOT_FOUND("516"),

    /** The children number must be positive. */
    CHILDREN_NUMBER_MUST_BE_POSITIVE("145"),

    /** The confirmation number not found. */
    CONFIRMATION_NUMBER_NOT_FOUND("134"),

    /** The create fnc failed. */
    CREATE_FNC_FAILED("172"),

    /** The credit card expiration date required. */
    CREDIT_CARD_EXPIRATION_DATE_REQUIRED("212"),

    /** The credit card expired. */
    CREDIT_CARD_EXPIRED("227"),

    /** The credit card name on card required. */
    CREDIT_CARD_NAME_ON_CARD_REQUIRED("211"),

    /** The credit card number required. */
    CREDIT_CARD_NUMBER_REQUIRED("210"),

    /** The customer order combination not found. */
    CUSTOMER_ORDER_COMBINATION_NOT_FOUND("5103"),

    /** The customer redemption not allowed. */
    CUSTOMER_REDEMPTION_NOT_ALLOWED("5247"),

    /** the date parsing. */
    DATE_PARSING("131"),

    /** The departure date too late. */
    DEPARTURE_DATE_TOO_LATE("181"),

    /** The departures file invalid xml format. */
    DEPARTURES_FILE_INVALID_XML_FORMAT("505"),

    /** The device does not exists. */
    DEVICE_DOES_NOT_EXISTS("502"),

    /** The device profiles does not exists. */
    DEVICE_NOT_BEING_USED("503"),

    /** The device push notification access denied. */
    DEVICE_PUSH_NOTIFICATION_ACCESS_DENIED("520"),

    /** The device used for different profile. */
    DEVICE_USED_FOR_DIFFERENT_PROFILE("504"),

    /** The duplicate ids. */
    DUCPLICATE_IDS("275"),

    /** The duplicate credit card. */
    DUPLICATE_CREDIT_CARD("225"),

    /** The duplicate emails. */
    DUPLICATE_EMAILS("327"),

    /** The duplicate favorite hotels. */
    DUPLICATE_FAVORITE_HOTELS("247"),

    /** The duplicate property exclusion. */
    DUPLICATE_PROPERTY_EXCLUSION("6100"),

    /** The duplicate returns numbers. */
    DUPLICATE_RETURNS_NUMBERS("201"),

    /** The duplicate returns profiles. */
    DUPLICATE_RETURNS_PROFILES("190"),

    /** The duplicated profile. */
    DUPLICATED_DEVICE("500"),

    /** The duplicated room amenity. */
    DUPLICATED_ROOM_AMENITY("287"),

    /** The email folio response invalid xml format. */
    EMAIL_FOLIO_RESPONSE_INVALID_XML_FORMAT("514"),

    /** The empty list input parameter. */
    EMPTY_LIST_INPUT_PARAMETER("122"),

    /** Auto-Upgrade: Empty Resevation List by Confirmation Numbers. */
    EMPTY_RESERVATION_LIST_BY_CONFIRMATION_NUMBERS("6000"),

    /** Error encrypting or decrypting the value. */
    ENCRYPTION_ERROR("274"),

    /** The epsilon input undeliverable. */
    EPSILON_ADDRESS_UNDELIVERABLE("301"),

    /** CHIRPIFY: Epsilon is not able to load given campaign code. */
    DRIVER_SESSION_EXPIRED("1403"),

    /** CHIRPIFY: Epsilon is not able to load given campaign code. */
    EPSILON_CHIRPIFY_VALIDATION_ERROR("1404"),

    /** The error finding hotel. */
    ERROR_FINDING_HOTEL("123"),

    /** The failed authentication exception. */
    FAILED_AUTHENTICATION_EXCEPTION("108"),

    /** The first name. */
    FIRST_NAME_EMPTY("249"),

    /** The first name invalid chars. */
    FIRST_NAME_INVALID_CHARS("293"),

    /** The last name too long. */
    FIRST_NAME_TOO_LONG("240"),

    /** The last name too long. */
    FIRST_NAME_TOO_SHORT("241"),

    /** The fnc enrollment status inactive. */
    FNC_ENROLLMENT_STATUS_INACTIVE("338"),

    /** The fnc enrollment status merged. */
    FNC_ENROLLMENT_STATUS_MERGED("340"),

    /** The fnc enrollment status onhold. */
    FNC_ENROLLMENT_STATUS_ONHOLD("339"),

    /** The future date. */
    FUTURE_DATE("102"),

    /** The generic code. */
    GENERIC_CODE("100"),

    /** The GET AVAILABILITY request invalid xml format. */
    GET_AVAILABILITY_REQUEST_INVALID_XML_FORMAT("6107"),

    /** The GET AVAILABILITY request invalid xml format. */
    GET_AVAILABILITY_RESPONSE_INVALID_XML_FORMAT("6106"),

    /** CHIRPIFY: If provided email does not match with profile email. */
    GIVEN_EMAIL_MISMATCH("355"),

    /** CHIRPIFY: If provided last name does not match with profile last name. */
    GIVEN_LASTNAME_MISMATCH("356"),

    /** CHIRPIFY: If provided last name does not match with profile last name. */
    GIVEN_MEMBERID_NOT_FOUND("357"),

    /** The guest information not found. */
    GUEST_INFORMATION_NOT_FOUND("138"),

    /** The hotel not found. */
    HOTEL_NOT_FOUND("125"),

    /** The id doesn't exists in db. */
    ID_DOESNT_EXIST_IN_DB("276"),

    /** The instant hold booking not possible. */
    INSTANT_HOLD_BOOKING_NOT_POSIBLE("314"),

    /** The instant hold payment type. */
    INSTANT_HOLD_CONVERSION_PAYMENT_TYPE("331"),

    /** The instant hold bookings requires a mobile phone number. */
    INSTANT_HOLD_REQUIRES_MOBILE_PHONE_NUMBER("313"),

    /** The instant hold requires phone guarantee. */
    INSTANT_HOLD_REQUIRES_PHONE_GUARANTEE("118"),

    /** The invalid input. */
    INVALID_ADDRESS("199"),

    /** The invalid input city. */
    INVALID_ADDRESS_CITY("195"),

    /** The invalid input country. */
    INVALID_ADDRESS_COUNTRY("197"),

    /** The invalid input country code length. */
    INVALID_ADDRESS_COUNTRY_CODE_LENGTH("305"),

    /** The invalid input postal. */
    INVALID_ADDRESS_POSTAL("306"),

    /** The invalid input state. */
    INVALID_ADDRESS_STATE("196"),

    /** The invalid input state code length. */
    INVALID_ADDRESS_STATE_CODE_LENGTH("298"),

    /** The invalid amenity id. */
    INVALID_AMENITY_ID("161"),

    /** The null booking channel. */
    INVALID_BOOKING_CHANNEL("164"),

    /** The trip advisor json parse. */
    INVALID_CAMPAING_ID("269"),

    /** The invalid card transaction id received. */
    INVALID_CARD_TRANSACTION_ID_RECEIVED("5259"),

    /** The invalid certificate length. */
    INVALID_CERTIFICATE_LENGTH("159"),

    /** The invalid certificate number. */
    INVALID_CERTIFICATE_NUMBER("234"),

    /** Invalid or null value for client req transaction id. */
    INVALID_CLIENT_REQUEST_TRANSACTION_ID("5423"),

    /** The invalid company name length. */
    INVALID_COMPANY_NAME_LENGTH("312"),

    /** The invalid confirmation number. */
    INVALID_CONFIRMATION_NUMBER("114"),

    /** The invalid credit card. */
    INVALID_CREDIT_CARD("169"),

    /** The invalid credit card type. */
    INVALID_CREDIT_CARD_TYPE("209"),

    /** The invalid currency. */
    INVALID_CURRENCY("105"),

    /** The invalid currency amount. */
    INVALID_CURRENCY_AMOUNT("104"),

    /** The invalid current password. */
    INVALID_CURRENT_PASSWORD("263"),

    /** The invalid data format in json parsing. */
    INVALID_DATA_FORMAT_JSON_PARSING("217"),

    /** The invalid date. */
    INVALID_DATE("208"),

    /** The invalid date range. */
    INVALID_DATE_RANGE("226"),

    /** The trip advisor json parse. */
    INVALID_DAYS_IN_ADVANCE("265"),

    /** The invalid description. */
    INVALID_DESCRIPTION("271"),

    /** The invalid device request. */
    INVALID_DEVICE_REQUEST("501"),

    /** The invalid device token iOS. */
    INVALID_DEVICE_TOKEN_IOS("506"),

    /** The invalid direct bill exception. */
    INVALID_DIRECT_BILL_EXCEPTION("107"),

    /** The invalid email length. */
    INVALID_EMAIL_LENGTH("309"),

    /** The invalid enrollment campaign code. */
    INVALID_ENROLLMENT_CAMPAIGN_CODE("281"),

    /** The invalid enrollment channel. */
    INVALID_ENROLLMENT_CHANNEL("282"),

    /** The invalid enrollment method. */
    INVALID_ENROLLMENT_METHOD("246"),

    /** The invalid enrollment source. */
    INVALID_ENROLLMENT_SOURCE("283"),

    /** The invalid filter option. */
    INVALID_FILTER_OPTION("239"),

    /** The invalid first and last name. */
    INVALID_FIRST_LAST_NAME("253"),

    /** The invalid first name. */
    INVALID_FIRST_NAME("191"),

    /** The invalid first name length. */
    INVALID_FIRST_NAME_LENGTH("295"),

    /** The invalid fnc and deposit required booking. */
    INVALID_FNC_DEPOSIT_REQUIRED_BOOKING("273"),

    /** The invalid folio number. */
    INVALID_FOLIO_NUMBER("231"),

    /** The invalid free night certificate status. */
    INVALID_FREE_NIGHT_CERTIFICATE_STATUS("235"),

    /** The invalid historical date range. */
    INVALID_HISTORICAL_DATE_RANGE("250"),

    /** The invalid home phone. */
    INVALID_HOME_PHONE("193"),

    /** The invalid hotel feature code. */
    INVALID_HOTEL_FEATURE_CODE("270"),

    /** The last name too long. */
    INVALID_IATA_FORMAT("244"),

    /** The invalid id. */
    INVALID_ID("272"),

    /** The invalid inn number. */
    INVALID_INN_NUMBER("115"),

    /** The invalid input. */
    INVALID_INPUT("119"),

    /** The invalid input for weather service. */
    INVALID_INPUT_FOR_WEATHER_SERVICE("350"),

    /** The invalid integer value. */
    INVALID_INTEGER_VALUE("213"),

    /** The invalid language pref code. */
    INVALID_LANGUAGE_PREF_CODE("254"),

    /** The invalid last name. */
    INVALID_LAST_NAME("192"),

    /** The invalid last name length. */
    INVALID_LAST_NAME_LENGTH("296"),

    /** The trip advisor json parse. */
    INVALID_LATITUDE_LONGITUDE("268"),

    /** The invalid latitude longitude range. */
    INVALID_LATITUDE_LONGITUDE_RANGE("292"),

    /** The invalid location value. */
    INVALID_LOCATION_VALUE("216"),

    /** The invalid loyalty amt to return. */
    INVALID_LOYALTY_AMT_TO_RETURN("5109"),

    /** The invalid loyalty batch id. */
    INVALID_LOYALTY_BATCH_ID("1052"),

    /** The invalid loyalty exchange rate. */
    INVALID_LOYALTY_EXCHANGE_RATE("5210"),

    /** The invalid ltv object. */
    INVALID_LTV_OBJECT("344"),

    /** The invalid merchant id. */
    INVALID_MERCHANT_ID("5201"),

    /** The invalid merchant order id. */
    INVALID_MERCHANT_ORDER_ID("5213"),

    /** The invalid merchant processor id. */
    INVALID_MERCHANT_PROCESSOR_ID("5202"),

    /** The invalid merchant sub identifier. */
    INVALID_MERCHANT_SUB_IDENTIFIER("5371"),

    /** The trip advisor json parse. */
    INVALID_METRO_AREA_NAME("267"),

    /** The invalid middle initial. */
    INVALID_MIDDLE_INITIAL("308"),

    /** The invalid middle initial length. */
    INVALID_MIDDLE_INITIAL_LENGTH("307"),

    /** The invalid mobile phone. */
    INVALID_MOBILE_PHONE("311"),

    /** The invalid notification method. */
    INVALID_NOTIFICATION_METHOD("334"),

    /** The invalid number of adults. */
    INVALID_NUMBER_OF_ADULTS("156"),

    /** The invalid number of children. */
    INVALID_NUMBER_OF_CHILDREN("158"),

    /** Invalid number of nights. */
    INVALID_NUMBER_OF_NIGHTS("178"),

    /** The invalid number of rooms. */
    INVALID_NUMBER_OF_ROOMS("157"),

    /** The invalid order description. */
    INVALID_ORDER_DESCRIPTION("5236"),

    /** The invalid order monetary amt. */
    INVALID_ORDER_MONETARY_AMT("5220"),

    /** The invalid order redemption type. */
    INVALID_ORDER_REDEMPTION_TYPE("5107"),

    /** The invalid partner code. */
    INVALID_REFERENCE_DATA("317"),

    /** The invalid partner item id. */
    INVALID_PARTNER_ITEM_ID("318"),

    /** The invalid partner registration. */
    INVALID_PARTNER_REGISTRATION("316"),

    /** The invalid password format. */
    INVALID_PASSWORD_FORMAT("116"),

    /** The invalid payment type. */
    INVALID_PAYMENT_TYPE("106"),

    /** The invalid payment type fnc. */
    INVALID_PAYMENT_TYPE_FNC("214"),

    /** The invalid phone. */
    INVALID_PHONE("198"),

    /** The message to change password for existing user. */
    CHANGE_YOUR_PASSWORD("450"),

    /** The invalid phone country code. */
    INVALID_PHONE_COUNTRY_CODE("329"),

    /** The invalid phone length. */
    INVALID_PHONE_LENGTH("310"),

    /** The invalid points bank id. */
    INVALID_POINTS_BANK_ID("5203"),

    /** The invalid preference. */
    INVALID_PREFERENCE("221"),

    /** The invalid primary credit card count. */
    INVALID_PRIMARY_CREDIT_CARD_COUNT("224"),

    /** The invalid primary email count. */
    INVALID_PRIMARY_EMAIL_COUNT("222"),

    /** The invalid primary phone count. */
    INVALID_PRIMARY_PHONE_COUNT("223"),

    /** The invalid processing status. */
    INVALID_PROCESSING_STATUS("5424"),

    /** The invalid program account. */
    INVALID_PROGRAM_ACCOUNT("5237"),

    /** The invalid proximity and inn list. */
    INVALID_PROXIMITY_AND_INN_LIST("330"),

    /** The invalid proximity list. */
    INVALID_PROXIMITY_LIST("299"),

    /** The trip advisor json parse. */
    INVALID_RANGE("264"),

    /** The invalid range input. */
    INVALID_RANGE_INPUT("188"),

    /** The invalid rate code. */
    INVALID_RATE_CODE("168"),

    /** Invalid or null value for req transaction id. */
    INVALID_REQUEST_TRANSACTION_ID("5200"),

    /** The invalid reservation. */
    INVALID_RESERVATION("166"),

    /** The invalid returns number. */
    INVALID_RETURNS_NUMBER("117"),

    /** The invalid returns points category. */
    INVALID_RETURNS_POINTS_CATEGORY("351"),

    /** The invalid returns points first name. */
    INVALID_RETURNS_POINTS_FIRST_NAME("352"),

    /** The invalid returns points last name. */
    INVALID_RETURNS_POINTS_LAST_NAME("353"),

    /** The invalid returns points points value. */
    INVALID_RETURNS_POINTS_POINTS_VALUE("354"),

    /** The invalid returns status. */
    INVALID_RETURNS_STATUS("5231"),

    /** The invalid reward id. */
    INVALID_REWARD_ID("326"),

    /** The invalid reward quantity. */
    INVALID_REWARD_QUANTITY("315"),

    /** The invalid reward quantity exceeded. */
    INVALID_REWARD_QUANTITY_EXCEEDED("328"),

    /** The invalid room code. */
    INVALID_ROOM_CODE("207"),

    /** The invalid room description id. */
    INVALID_ROOM_DESCRIPTION_ID("230"),

    /** The null booking channel. */
    INVALID_ROOM_READY_NOTIFICATION_USAGE("260"),

    /** The invalid source data file. */
    INVALID_SOURCE_DATA_FILE("204"),

    /** The invalid split tender. */
    INVALID_SPLIT_TENDER("5324"),

    /** The trip advisor json parse. */
    INVALID_STATE_AND_COUNTRY("266"),

    /** The invalid total order loyalty price. */
    INVALID_TOTAL_ORDER_LOYALTY_PRICE("5209"),

    /** The invalid total order price. */
    INVALID_TOTAL_ORDER_PRICE("5208"),

    /** The invalid transaction channel. */
    INVALID_TRANSACTION_CHANNEL("5352"),

    /** The invalid unit. */
    INVALID_UNIT("160"),

    /** The invalid wallet currency code. */
    INVALID_WALLET_CURRENCY_CODE("322"),

    /** The invalid wallet description. */
    INVALID_WALLET_DESCRIPTION("323"),

    /** The invalid wallet origin. */
    INVALID_WALLET_ORIGIN("319"),

    /** The last name. */
    LAST_NAME_EMPTY("176"),

    /** The last name invalid chars. */
    LAST_NAME_INVALID_CHARS("294"),

    /** The last name too long. */
    LAST_NAME_TOO_LONG("242"),

    /** The last name too short. */
    LAST_NAME_TOO_SHORT("243"),

    /** The malformed date. */
    MALFORMED_DATE("101"),

    /** The malformed property id. */
    MALFORMED_PROPERTY_ID("155"),

    /** The malformed returns number. */
    MALFORMED_RETURNS_NUMBER("218"),

    /** The invalid integer value. */
    MASKED_CREDIT_CARD_AND_NO_RETURNS_NUMBER("220"),

    /** Mismatch number of nights. */
    MISMATCH_NUMBER_OF_NIGHTS("325"),

    /** The mismatch requested points. */
    MISMATCH_REQUESTED_POINTS("291"),

    /** The missing email input for email service. */
    MISSING_EMAIL_FOR_EMAIL_SERVICE("345"),

    /** The missing email for password reset. */
    MISSING_EMAIL_FOR_PASSWORD_RESET("261"),

    /** The missing last name. */
    MISSING_LAST_NAME("347"),

    /** The missing notification data. */
    MISSING_NOTIFICATION_DATA("337"),

    /** The missing notification recipients. */
    MISSING_NOTIFICATION_RECIPIENTS("335"),

    /** The missing notification template. */
    MISSING_NOTIFICATION_TEMPLATE("336"),

    /** The missing password. */
    MISSING_PASSWORD("110"),

    /** The missing phone designation. */
    MISSING_PHONE_DESIGNATION("290"),

    /** The missing phone list. */
    MISSING_PHONE_LIST("258"),

    /** The missing returns id. */
    MISSING_RETURNS_ID("109"),

    /** The arrival date too early. */
    MORE_THAN_ONE_CREDIT_CARD("186"),

    /** The more than one year between dates. */
    MORE_THAN_ONE_YEAR_BETWEEN_DATES("297"),

    /** The MSI Checkout Response Error Message. */
    MSI_RESPONSE_ERROR_MESSAGE("515"),

    /** The adult number must be positive. */
    MULTIPLE_BUSINESS_ERRORS("200"),

    /** The multiple phones by designation. */
    MULTIPLE_PHONES_BY_DESIGNATION("259"),

    /** The name on cc and name on booking. */
    NAME_ON_CC_AND_NAME_BOOKING_DOESNT_MATCH("278"),

    /** The names are repeated. */
    NAMES_REPEATED("277"),

    /** The no availability for this room. */
    NO_AVAILABILITY_FOR_THIS_ROOM("284"),

    /** The no direct bill available. */
    NO_DIRECT_BILL_AVAILABLE("179"),

    /** The no free night certificate found. */
    NO_FREE_NIGHT_CERTIFICATE_FOUND("233"),

    /** No properties near to location. */
    NO_PROPERTIES_NEAR_TO_LOCATION("508"),

    /** The duplicate property exclusion. */
    NO_SAME_DAY_PROPERTY_EXCLUSIONS("6102"),

    /** Not enough points. */
    NOT_ENOUGH_POINTS("232"),

    // /** The pegaus response parsing error. */
    // PEGAUS_RESPONSE_PARSING_ERROR("126"),

    /** The not implemented. */
    NOT_IMPLEMENTED("163"),

    // /** The pegaus response parsing error. */
    // PEGAUS_RESPONSE_PARSING_ERROR("126"),

    /** Error sending Notification. */
    NOTIFICATION_ERROR("228"),

    // /** The pegaus response parsing error. */
    // PEGAUS_RESPONSE_PARSING_ERROR("126"),

    /** The null arrival date. */
    NULL_ARRIVAL_DATE("255"),

    /** The null customer number. */
    NULL_CUSTOMER_NUMBER("151"),

    /** The null customer name. */
    NULL_CUSTOMER_NAME("152"),

    /** The null arrival date. */
    NULL_DEPARTURE_DATE("256"),

    /** The null first name. */
    NULL_FIRST_NAME("251"),

    /** The null inn number. */
    NULL_INN_NUMBER("152"),

    /** The null input parameter. */
    NULL_INPUT_PARAMETER("121"),

    /** The null last name. */
    NULL_LAST_NAME("252"),

    /** The null rooms. */
    NULL_ROOMS("154"),

    /** The ota invalid access id. */
    OTA_ACCESS_ID_INVALID("1059"),

    /** The ota missing access. */
    OTA_ACCESS_MISSING("1058"),

    /** The ota access too many access. */
    OTA_ACCESS_TOO_MANY_ACCESS("1060"),

    /** The ota missing accessestype. */
    OTA_ACCESSESTYPE_MISSING("1057"),

    /** The ota input addres line information not found. */
    OTA_ADDRESS_ADDRES_LINE_INFORMATION_NOT_FOUND("1024"),

    /** The ota input city name information not found. */
    OTA_ADDRESS_CITY_NAME_INFORMATION_NOT_FOUND("1020"),

    /** The ota input country code information not found. */
    OTA_ADDRESS_COUNTRY_CODE_INFORMATION_NOT_FOUND("1025"),

    /** The ota input country code only can have 2 digits. */
    OTA_ADDRESS_COUNTRY_CODE_ONLY_2_DIGITS("1026"),

    /** The ota input country name information not found. */
    OTA_ADDRESS_COUNTRY_NAME_INFORMATION_NOT_FOUND("1021"),

    /** The ota input postal code information not found. */
    OTA_ADDRESS_POSTAL_CODE_INFORMATION_NOT_FOUND("1022"),

    /** The ota input stateprov code information not found. */
    OTA_ADDRESS_STATEPROV_CODE_INFORMATION_NOT_FOUND("1027"),

    /** The ota input stateprov information not found. */
    OTA_ADDRESS_STATEPROV_INFORMATION_NOT_FOUND("1023"),

    /** The ota booking channel information not found. */
    OTA_BOOKING_CHANNEL_INFORMATION_NOT_FOUND("1002"),

    /** The ota booking channel type information not found. */
    OTA_BOOKING_CHANNEL_TYPE_INFORMATION_NOT_FOUND("1003"),

    /** The ota booking channel type invalid value. */
    OTA_BOOKING_CHANNEL_TYPE_INVALID_VALUE("1040"),

    /** The ota company info not found. */
    OTA_COMPANY_INFO_NOT_FOUND("1046"),

    /** The ota customer input information not found. */
    OTA_CUSTOMER_ADDRESS_INFORMATION_NOT_FOUND("1011"),

    /** The ota customer input information only required once. */
    OTA_CUSTOMER_ADDRESS_INFORMATION_ONLY_REQUIRED_ONCE("1012"),

    /** The ota customer email information at least required once. */
    OTA_CUSTOMER_EMAIL_INFORMATION_AT_LEAST_REQUIRED_ONCE("1014"),

    /** The ota customer email information not found. */
    OTA_CUSTOMER_EMAIL_INFORMATION_NOT_FOUND("1013"),

    /** The ota customer given name information not found. */
    OTA_CUSTOMER_GIVEN_NAME_INFORMATION_NOT_FOUND("1017"),

    /** The ota customer given name information only required once. */
    OTA_CUSTOMER_GIVEN_NAME_INFORMATION_ONLY_REQUIRED_ONCE("1018"),

    /** The ota customer given name invalid length. */
    OTA_CUSTOMER_GIVEN_NAME_INVALID_LENGTH("1067"),

    /** The ota customer information not found. */
    OTA_CUSTOMER_INFORMATION_NOT_FOUND("1007"),

    /** The ota customer middle name invalid length. */
    OTA_CUSTOMER_MIDDLE_NAME_INVALID_LENGTH("1039"),

    /** The ota customer personname information not found. */
    OTA_CUSTOMER_PERSONNAME_INFORMATION_NOT_FOUND("1009"),

    /** The ota customer personname information only required once. */
    OTA_CUSTOMER_PERSONNAME_INFORMATION_ONLY_REQUIRED_ONCE("1010"),

    /** The ota customer surname information not found. */
    OTA_CUSTOMER_SURNAME_INFORMATION_NOT_FOUND("1019"),

    /** The ota customer surname invalid length. */
    OTA_CUSTOMER_SURNAME_INVALID_LENGTH("1068"),

    /** The ota customer telephone information at least required once. */
    OTA_CUSTOMER_TELEPHONE_INFORMATION_AT_LEAST_REQUIRED_ONCE("1016"),

    /** The ota customer telephone information not found. */
    OTA_CUSTOMER_TELEPHONE_INFORMATION_NOT_FOUND("1015"),

    /** The ota email input invalid. */
    OTA_EMAIL_ADDRESS_INVALID("1063"),

    /** The ota email input not found. */
    OTA_EMAIL_ADDRESS_NOT_FOUND("1030"),

    /** The ota email personal type required. */
    OTA_EMAIL_PERSONAL_TYPE_REQUIRED("1042"),

    /** The ota email primary required. */
    OTA_EMAIL_PRIMARY_REQUIRED("1043"),

    /** The ota email type information not found. */
    OTA_EMAIL_TYPE_INFORMATION_NOT_FOUND("1028"),

    /** The ota email type invalid value. */
    OTA_EMAIL_TYPE_INVALID_VALUE("1029"),

    /** The ota extension enrollment channel invalid value. */
    OTA_EXTENSION_ENROLLMENT_CHANNEL_INVALID_VALUE("1111"),

    /** The ota extension enrollment channel not found. */
    OTA_EXTENSION_ENROLLMENT_CHANNEL_NOT_FOUND("1109"),

    /** The ota extension enrollment source invalid value. */
    OTA_EXTENSION_ENROLLMENT_SOURCE_INVALID_VALUE("1112"),

    /** The ota extension enrollment source not found. */
    OTA_EXTENSION_ENROLLMENT_SOURCE_NOT_FOUND("1110"),

    /** The ota extension not found. */
    OTA_EXTENSION_NOT_FOUND("1108"),

    /** The ota invalid iso language preference code. */
    OTA_INVALID_ISO_LANGUAGE_PREFERENCE_CODE("1008"),

    /** The ota invalid military code context. */
    OTA_INVALID_MILITARY_CODE_CONTEXT("1054"),

    /** The ota loyalty level invalid. */
    OTA_LOYALTY_LEVEL_INVALID("1048"),

    /** The ota loyalty level not found. */
    OTA_LOYALTY_LEVEL_NOT_FOUND("1047"),

    /** The ota loyalty signup date not found. */
    OTA_LOYALTY_SIGNUP_DATE_NOT_FOUND("1072"),

    /** The ota loyalty too many programs. */
    OTA_LOYALTY_TOO_MANY_PROGRAMS("1049"),

    /** The ota pegasus input addressline invalid length. */
    OTA_PEGASUS_ADDRESS_ADDRESSLINE_INVALID_LENGTH("1093"),

    /** The ota pegasus input city name invalid length. */
    OTA_PEGASUS_ADDRESS_CITY_NAME_INVALID_LENGTH("1094"),

    /** The ota pegasus input country name not found. */
    OTA_PEGASUS_ADDRESS_COUNTRY_NAME_NOT_FOUND("1096"),

    /** The ota pegasus input postal code invalid length. */
    OTA_PEGASUS_ADDRESS_POSTAL_CODE_INVALID_LENGTH("1097"),

    /** The ota pegasus input stateprov not found. */
    OTA_PEGASUS_ADDRESS_STATEPROV_NOT_FOUND("1095"),

    /** The ota pegasus countryname code invalid length. */
    OTA_PEGASUS_COUNTRYNAME_CODE_INVALID_LENGTH("1100"),

    /** The ota pegasus countryname code invalid value. */
    OTA_PEGASUS_COUNTRYNAME_CODE_INVALID_VALUE("1101"),

    /** The ota pegasus customer input not found. */
    OTA_PEGASUS_CUSTOMER_ADDRESS_NOT_FOUND("1087"),

    /** The ota pegasus customer custloyalty not found. */
    OTA_PEGASUS_CUSTOMER_CUSTLOYALTY_NOT_FOUND("1088"),

    /** The ota pegasus customer email not found. */
    OTA_PEGASUS_CUSTOMER_EMAIL_NOT_FOUND("1086"),

    /** The ota pegasus customer not found. */
    OTA_PEGASUS_CUSTOMER_NOT_FOUND("1083"),

    /** The ota pegasus customer personname not found. */
    OTA_PEGASUS_CUSTOMER_PERSONNAME_NOT_FOUND("1084"),

    /** The ota pegasus customer telephone invalid length. */
    OTA_PEGASUS_CUSTOMER_TELEPHONE_INVALID_LENGTH("1085"),

    /** The ota pegasus email email type not found. */
    OTA_PEGASUS_EMAIL_EMAIL_TYPE_NOT_FOUND("1106"),

    /** The ota pegasus email value invalid length. */
    OTA_PEGASUS_EMAIL_VALUE_INVALID_LENGTH("1105"),

    /** The ota pegasus middle name invalid length. */
    OTA_PEGASUS_MIDDLE_NAME_INVALID_LENGTH("1091"),

    /** The ota pegasus personname given name invalid length. */
    OTA_PEGASUS_PERSONNAME_GIVEN_NAME_INVALID_LENGTH("1090"),

    /** The ota pegasus personname nameprefix invalid length. */
    OTA_PEGASUS_PERSONNAME_NAMEPREFIX_INVALID_LENGTH("1089"),

    /** The ota pegasus profile not found. */
    OTA_PEGASUS_PROFILE_NOT_FOUND("1081"),

    /** The ota pegasus profileinfo invalid length. */
    OTA_PEGASUS_PROFILEINFO_INVALID_LENGTH("1079"),

    /** The ota pegasus profiles not found. */
    OTA_PEGASUS_PROFILES_NOT_FOUND("1078"),

    /** The ota pegasus stateprov state code invalid length. */
    OTA_PEGASUS_STATEPROV_STATE_CODE_INVALID_LENGTH("1098"),

    /** The ota pegasus stateprov state code invalid value. */
    OTA_PEGASUS_STATEPROV_STATE_CODE_INVALID_VALUE("1099"),

    /** The ota pegasus surname invalid length. */
    OTA_PEGASUS_SURNAME_INVALID_LENGTH("1092"),

    /** The ota pegasus telephone phone location type not found. */
    OTA_PEGASUS_TELEPHONE_PHONE_LOCATION_TYPE_NOT_FOUND("1103"),

    /** The ota pegasus telephone phone number invalid length. */
    OTA_PEGASUS_TELEPHONE_PHONE_NUMBER_INVALID_LENGTH("1102"),

    /** The ota pegasus telephone phone tech type not found. */
    OTA_PEGASUS_TELEPHONE_PHONE_TECH_TYPE_NOT_FOUND("1104"),

    /** The ota pegasus uniqueid id invalid length. */
    OTA_PEGASUS_UNIQUEID_ID_INVALID_LENGTH("1082"),

    /** The ota pegasus uniqueid invalid length. */
    OTA_PEGASUS_UNIQUEID_INVALID_LENGTH("1080"),

    /** The ota pos information not found. */
    OTA_POS_INFORMATION_NOT_FOUND("1000"),

    /** The ota requestor id invalid value. */
    OTA_REQUESTOR_ID_INVALID_VALUE("1107"),

    /** The ota requestor id name invalid length. */
    OTA_REQUESTOR_ID_NAME_INVALID_LENGTH("1075"),

    /** The ota requestor id name not found. */
    OTA_REQUESTOR_ID_NAME_NOT_FOUND("1074"),

    /** The ota requestor id not found. */
    OTA_REQUESTOR_ID_NOT_FOUND("1073"),

    /** The ota schema validation failed. */
    OTA_SCHEMA_VALIDATION_FAILED("1076"),

    /** The ota source information not found. */
    OTA_SOURCE_INFORMATION_NOT_FOUND("1001"),

    /** The ota status code invalid value. */
    OTA_STATUS_CODE_INVALID_VALUE("1071"),

    /** The ota status code is required once. */
    OTA_STATUS_CODE_IS_REQUIRED_ONCE("1070"),

    /** The ota status code not found. */
    OTA_STATUS_CODE_NOT_FOUND("1069"),

    /** The ota telephone area city code not found. */
    OTA_TELEPHONE_AREA_CITY_CODE_NOT_FOUND("1031"),

    /** The ota telephone area city code no less than 1 digit. */
    OTA_TELEPHONE_AREA_CITY_CODE_NOT_LESS_THAN_1_DIGIT("1032"),

    /** The ota telephone country code invalid characters. */
    OTA_TELEPHONE_COUNTRY_CODE_INVALID_CHARACTERS("1077"),

    /** The ota telephone area country code not found. */
    OTA_TELEPHONE_COUNTRY_CODE_NOT_FOUND("1033"),

    /** The ota telephone home phone use type required. */
    OTA_TELEPHONE_HOME_PHONE_USE_TYPE_REQUIRED("1041"),

    /** The ota telephone phone number invalid characters. */
    OTA_TELEPHONE_PHONE_NUMBER_INVALID_CHARACTERS("1038"),

    /** The ota telephone phone number not found. */
    OTA_TELEPHONE_PHONE_NUMBER_NOT_FOUND("1036"),

    /** The ota telephone phone number only 7 digits. */
    OTA_TELEPHONE_PHONE_NUMBER_ONLY_7_DIGITS("1037"),

    /** The ota telephone phone use type invalid value. */
    OTA_TELEPHONE_PHONE_USE_TYPE_INVALID_VALUE("1035"),

    /** The ota telephone phone use type not found. */
    OTA_TELEPHONE_PHONE_USE_TYPE_NOT_FOUND("1034"),

    /** The ota telephone primary required. */
    OTA_TELEPHONE_PRIMARY_REQUIRED("1044"),

    /** The ota too many emails with same designation. */
    OTA_TOO_MANY_EMAILS_WITH_SAME_DESIGNATION("1066"),

    /** The ota too many phones with same designation. */
    OTA_TOO_MANY_PHONES_WITH_SAME_DESIGNATION("1065"),

    /** The ota uniqueid id information not found. */
    OTA_UNIQUEID_ID_INFORMATION_NOT_FOUND("1005"),

    /** The ota uniqueid information not found. */
    OTA_UNIQUEID_INFORMATION_NOT_FOUND("1004"),

    /** The ota uniqueid type information not found. */
    OTA_UNIQUEID_TYPE_INFORMATION_NOT_FOUND("1045"),

    /** The ota uniqueid type invalid. */
    OTA_UNIQUEID_TYPE_INVALID("1064"),

    /** The ota user id pin invalid. */
    OTA_USER_ID_PIN_INVALID("1061"),

    /** The payment info not found. */
    PAYMENT_INFO_NOT_FOUND("142"),

    /** The pegasus duplicated returns profile. */
    PEGASUS_DUPLICATED_RETURNS_PROFILE("1113"),

    /** The pegasus error node found. */
    PEGASUS_ERROR_NODE_FOUND("167"),

    /** The pegasus response fatal error. */
    PEGASUS_RESPONSE_FATAL_ERROR("129"),

    /** The missing arrival date. */
    PEL_MISSING_ARRIVAL_DATE("3004"),

    /** The missing body. */
    PEL_MISSING_BODY_PART_IN_REQUEST("3000"),

    /** The missing departure date. */
    PEL_MISSING_DEPARTURE_DATE("3005"),

    /** The missing hotel code. */
    PEL_MISSING_HOTEL_CODE("3003"),

    /** The missing or empty reservation list. */
    PEL_MISSING_OR_EMPTY_RESERVATION_LIST("3001"),

    /** The missing or empty reservation number. */
    PEL_MISSING_OR_EMPTY_RESERVATION_NUMBER("3002"),

    /** The missing parameters. */
    PEL_MISSING_PARAMETERS_PART_IN_REQUEST("3007"),

    /** The missing name. */
    PEL_NAME_CAN_NOT_BE_EMPTY("3006"),

    /** The property does not exist. */
    PROPERTY_DOES_NOT_EXIST("202"),

    /** The property not available. */
    PROPERTY_DOESNT_HAVE_AVAILABILITY("285"),

    /** The property exclusion end date invalid. */
    PROPERTY_EXCLUSION_END_DATE_INVALID("6104"),

    /** The property exclusion not found. */
    PROPERTY_EXCLUSION_NOT_FOUND("6101"),

    /** The property exclusion start date invalid. */
    PROPERTY_EXCLUSION_START_DATE_INVALID("6103"),

    /** The property exclusion start date required. */
    PROPERTY_EXCLUSION_START_DATE_REQUIRED("6105"),

    /** The property information not found. */
    PROPERTY_INFORMATION_NOT_FOUND("139"),

    /** The property not available. */
    PROPERTY_NOT_AVAILABLE("184"),

    /** The quoted total rate and no credit card error. */
    QUOTED_TOTAL_RATE_AND_NO_CREDIT_CARD("187"),

    /** The crs code is empty. */
    RATE_CODE_CRS_CODE_IS_EMPTY("174"),

    /** The rate code not found id. */
    RATE_CODE_NOT_FOUND_ID("111"),

    /** The rate code plan is not FNC. */
    RATE_CODE_PLAN_IS_NOT_FNC("183"),

    /** The rate plan information not found. */
    RATE_PLAN_INFORMATION_NOT_FOUND("137"),

    /** Invalid or null value for monetary currency type. */
    REDEEM_INVALID_MONETARY_CURRENCY_TYPE("5211"),

    /** Invalid or null value for redeemed loyalty amount. */
    REDEEM_INVALID_REDEEMED_LOYALTY_AMOUNT("5422"),

    /** Invalid or null value for redeemed monetary amount. */
    REDEEM_INVALID_REDEEMED_MONETARY_AMOUNT("5421"),

    /** Invalid or null value for Redemption Status. */
    REDEEM_INVALID_REDEMPTION_STATUS("5010"),

    /** Invalid or null value for statement credit id. */
    REDEEM_INVALID_STATEMENT_CREDIT_ID("5383"),

    /** The redeem phone number is not unique for enrollment. */
    REDEEM_PHONE_NUMBER_IS_NOT_UNIQUE_FOR_ENROLLMENT("5001"),

    /** The redeem phone number is registered for multiple members. */
    REDEEM_PHONE_NUMBER_IS_REGISTERED_FOR_MULTIPLE_MEMBERS("5002"),

    /** Duplicate request for submit transaction. */
    REDEEM_SUBMIT_TRANS_DUPLICATE_REQ_TRANS_ID("5245"),

    /** Invalid or null value for available loyalty amount for redemption. */
    REDEEM_SUBMIT_TRANS_INVALID_AVAILABLE_LOYALTY_AMT_FOR_RED("5419"),

    /** Invalid or null value for customer loyalty balance. */
    REDEEM_SUBMIT_TRANS_INVALID_CUSTOMER_LOYALTY_BALANCE("5348"),

    /** Invalid or null value for customer monetary balance. */
    REDEEM_SUBMIT_TRANS_INVALID_CUSTOMER_MONETARY_BALANCE("5420"),

    /** Invalid or null value for Merchant Category Code. */
    REDEEM_SUBMIT_TRANS_INVALID_MERCHANT_CATEGORY_CODE("5378"),

    /** Invalid or null value for monetary amount for redemption. */
    REDEEM_SUBMIT_TRANS_INVALID_MONETARY_AMT_FOR_RED("5418"),

    /** Invalid or null value for Purchase Category. */
    REDEEM_SUBMIT_TRANS_INVALID_PURCHASE_CATEGORY("5369"),

    /** Invalid or null value for rewards account number. */
    REDEEM_SUBMIT_TRANS_INVALID_REWARDS_ACCOUNT_NUMBER("5327"),

    /** Invalid or null value for Transaction equivalent loyalty amount. */
    REDEEM_SUBMIT_TRANS_INVALID_TRAN_EQ_LOYALTY_AMOUNT("5417"),

    /** Invalid or null value for Transaction Amount. */
    REDEEM_SUBMIT_TRANS_INVALID_TRANS_AMOUNT("5381"),

    /** Invalid or null value for Transaction Id. */
    REDEEM_SUBMIT_TRANS_INVALID_TRANS_ID("5373"),

    /** The redemption exceeds customer available balance. */
    REDEMPTION_EXCEEDS_CUSTOMER_AVAILABLE_BALANCE("5229"),

    /** The repeated description. */
    REPEATED_DESCRIPTION("280"),

    /** The reservation details not found. */
    RESERVATION_DETAILS_NOT_FOUND("165"),

    /** The reservation is locked. */
    RESERVATION_IS_LOCKED("302"),

    /** The reservation not modifiable. */
    RESERVATION_NOT_MODIFIABLE("219"),

    /** The returns id and email not set. */
    RETURNS_ID_AND_EMAIL_NOT_SET("113"),

    /** The returns id and email set. */
    RETURNS_ID_AND_EMAIL_SET("112"),

    /** The returns id and last name not set. */
    RETURNS_ID_AND_LAST_NAME_NOT_SET("346"),

    /** The returns number doesn't exists. */
    RETURNS_NUMBER_DOESNT_EXISTS("279"),

    /** The no enough points to stay. */
    RETURNS_NUMBER_NO_ENOUGH_POINTS_TO_STAY("215"),

    /** The room amenity deleted. */
    ROOM_AMENITY_DELETED("130"),

    /** The room amenity not found. */
    ROOM_AMENITY_NOT_FOUND("286"),

    /** The room code not found id. */
    ROOM_CODE_NOT_FOUND_ID("245"),

    /** The room description deleted. */
    ROOM_DESCRIPTION_DELETED("124"),

    /** The room description not found. */
    ROOM_DESCRIPTION_NOT_FOUND("289"),

    /** The room id not found. */
    ROOM_ID_NOT_FOUND("162"),

    /** The room information not found. */
    ROOM_INFORMATION_NOT_FOUND("135"),

    /** The room not available. */
    ROOM_NOT_AVAILABLE("185"),

    /** The room not found. */
    ROOM_NOT_FOUND("177"),

    /** The room number must be positive. */
    ROOM_NUMBER_MUST_BE_POSITIVE("143"),

    /** The crs code is empty. */
    ROOM_TYPE_CRS_CODE_IS_EMPTY("175"),

    /** The suspicious returns account. */
    SUSPICIOUS_RETURNS_ACCOUNT("343"),

    /** Error applying Templates. */
    TEMPLATE_ERROR("229"),

    /** The time span information not found. */
    TIME_SPAN_INFORMATION_NOT_FOUND("136"),

    /** The tokenization failed. */
    TOKENIZATION_FAILED_EXCEPTION("348"),

    /** The too many adults. */
    TOO_MANY_ADULTS("147"),

    /** The too many children. */
    TOO_MANY_CHILDREN("148"),

    /** The too many emails with same designation. */
    TOO_MANY_EMAILS_WITH_SAME_DESIGNATION("333"),

    /** The too many favorite hotels. */
    TOO_MANY_FAVORITE_HOTELS("248"),

    /** The too many guests. */
    TOO_MANY_GUESTS("182"),

    /** The too many phones with same designation. */
    TOO_MANY_PHONES_WITH_SAME_DESIGNATION("332"),

    /** The too many profiles assigned. */
    TOO_MANY_PROFILES_ASSIGNED("300"),

    /** The too many returns summaries. */
    TOO_MANY_RETURNS_SUMMARIES("349"),

    /** The too many rooms. */
    TOO_MANY_ROOMS("146"),

    /** The total node not found. */
    TOTAL_NODE_NOT_FOUND("140"),

    /** The update merged returns member. */
    UPDATE_MERGED_RETURNS_MEMBER("342"),

    /** The update ohhold returns member. */
    UPDATE_OHHOLD_RETURNS_MEMBER("341"),

    /** The user has already a reservation. */
    USER_HAS_ALREADY_A_RESERVATION("507"),

    /** The user is not eligible to do mobile checkout due to either the reservation cache don't have that entry or have more than one reservations. */
    USER_NOT_ELIGIBLE_TO_CHECKOUT("509"),
    
    /** APP version used is not compatible with the API */
    INCOMPATIBLE_APP_VERSION("350");

    /** The message number. */
    private final String messageNumber;

    /**
     * Instantiates a new business exception code.
     *
     * @param messageNumber the message number
     */
    BusinessExceptionCode(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    /**
     * Gets the BusinessExceptionCode for a message number.
     *
     * @param messageNumber the message number
     * @return the BusinessExceptionCode or null if not found
     */
    public static BusinessExceptionCode forMessageNumber(String messageNumber) {
        BusinessExceptionCode code = null;
        if (messageNumber == null) {
            return null;
        }
        for (BusinessExceptionCode businessExceptionCode : BusinessExceptionCode.values()) {
            if (messageNumber.equals(businessExceptionCode.getMessageNumber())) {
                code = businessExceptionCode;
                break;
            }
        }
        return code;
    }

    /**
     * Gets the message number.
     *
     * @return the message number
     */
    public String getMessageNumber() {
        return messageNumber;
    }
}
