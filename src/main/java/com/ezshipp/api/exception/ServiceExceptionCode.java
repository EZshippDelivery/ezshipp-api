package com.ezshipp.api.exception;

// TODO: Auto-generated Javadoc
/**
 * The Enum ServiceExceptionCode provides a code for all service exceptions so that we can map them correctly in the resource bundle.
 */
public enum ServiceExceptionCode {

    /** The excel generation error. */
    EXCEL_GENERATION_ERROR("1500"),

    /** The Update failed. */
    UPDATE_FAILED("117"),

    DRIVER_CREATE_FAILED("118"),
    DRIVER_UPDATE_FAILED("128"),
    CUSTOMER_CREATE_FAILED("218"),
    ORDER_NOT_FOUND("219"),

    SMS_SEND_FAILED("119"),
    POST_ORDER_MESSAGE("801"),

    GET_COUNTRY_BY_IP_FAILED("122"),


    PUSH_NOTIFICATION_FAILED("120"),
    INVALID_ENTITY_ID("130"),

    /** COD update failed. */
    COD_UPDATE_FAILED("1006"),

    /** COD update failed. */
    BIKER_PAYMENT_UPDATE_FAILED("1007"),

    /**
     * The service is not available.
     */
    BOOKING_SERVICE_UNAVAILABLE("162"),

    /** The EPSILON s_ no n_200. */
    CANCEL_FAILURE("144"),

    /**
     * The service is not available.
     */
    CANCEL_RESERVATION_SERVICE_UNAVAILABLE("163"),

    /**
     * The call center service database error.
     */
    CCS_DATABASE_ERROR("2000"),

    /**
     * The service is not available.
     */
    CHECKOUT_PMS_SERVICE_UNAVAILABLE("504"),

    /** The checkout request schema validation error. */
    CHECKOUT_REQUEST_SCHEMA_VALIDATION_ERROR("502"),

    /** The checkout response schema validation error. */
    CHECKOUT_RESPONSE_SCHEMA_VALIDATION_ERROR("503"),

    /** The client abort exception. */
    CLIENT_ABORT_EXCEPTION("209"),

    /** The confirmation number doesn't match. */
    CONFIRMATION_NUMBERS_DOESNT_MATCH("140"),

    /** The connection failure. */
    CONNECTION_FAILURE("193"),

    /** The credit card decryption error. */
    CREDIT_CARD_DECRYPTION_ERROR("119"),

    /** The credit card encryption error. */
    CREDIT_CARD_ENCRYPTION_ERROR("120"),

    /** Rate code not found. */
    CRS_CODE_NOT_FOUND("137"),

    /** CRS room type code not found. */
    CRS_ROOM_TYPE_CODE_NOT_FOUND("138"),

    /** The database error. */
    DATABASE_ERROR("185"),

    /** The date parsing. */
    DATE_PARSING("160"),

    /** The departures file schema validation error. */
    DEPARTURES_FILE_SCHEMA_VALIDATION_ERROR("500"),

    /** The departures file schema validation error. */
    DEPARTURES_PUSH_NOTIFICATION_ERROR("501"),

    /** The deposit required. */
    DEPOSIT_REQUIRED("203"),

    /** The distributor service unavailable. */
    DISTRIBUTOR_SERVICE_UNAVAILABLE("214"),

    /** The EMAIL FOLIO PMS service not available. */
    EMAIL_FAILURE("505"),

    /** The email parsing. */
    EMAIL_PARSING("198"),

    /** The empty hotel room rates. */
    EMPTY_HOTEL_ROOM_RATES("118"),

    /** The epsilon db error. */
    EPSILON_DB_ERROR("136"),

    /** The epsilon invalid profile information. */
    EPSILON_INVALID_PROFILE_INFORMATION("1002"),

    /** The unmarshalling error. */
    /** The EPSILON s_ no n_200. */
    EPSILON_NON_200("116"),

    /** The epsilon points insufficient points. */
    EPSILON_POINTS_INSUFFICIENT_POINTS("225"),

    /** The epsilon points invalid member id. */
    EPSILON_POINTS_INVALID_MEMBER_ID("226"),

    /** The epsilon points invalid member status. */
    EPSILON_POINTS_INVALID_MEMBER_STATUS("227"),

    /** The epsilon points service error. */
    EPSILON_POINTS_SERVICE_ERROR("228"),

    /** The epsilon points service unavailable. */
    EPSILON_POINTS_SERVICE_UNAVAILABLE("229"),

    /** The epsilon profile service unavailable. */
    EPSILON_PROFILE_SERVICE_UNAVAILABLE("1117"),

    /** The EPSILON parsing response error. */
    EPSILON_RESPONSE_PARSER("115"),

    /** The epsilon sync error. */
    EPSILON_SYNC_ERROR("141"),

    /** The EPSILON unreachable. */
    EPSILON_UNREACHABLE("114"),

    /**
     * The service is not available.
     */
    FIND_RESERVATION_BY_CONFIRMATION_SERVICE_UNAVAILABLE("164"),

    /** The service is not available. */
    FIND_RESERVATION_BY_FOLIO_AND_INN_NUMBER_SERVICE_UNAVAILABLE("231"),

    /**
     * The service is not available.
     */
    FIND_RESERVATION_BY_RETURN_SERVICE_UNAVAILABLE("165"),

    /** The fnc creation error. */
    FNC_CREATION_ERROR("151"),

    /** The fnc rate code tier not found. */
    FNC_RATE_CODE_TIER_NOT_FOUND("121"),

    /**
     * The service is not available.
     */
    GET_AVAILABILITY_PMS_SERVICE_UNAVAILABLE("6110"),

    /** The get availability request schema validation error. */
    GET_AVAILABILITY_REQUEST_SCHEMA_VALIDATION_ERROR("6111"),

    /** The get availability response schema validation error. */
    GET_AVAILABILITY_RESPONSE_SCHEMA_VALIDATION_ERROR("6112"),

    /** The get awards failed. */
    GET_AWARDS_FAILED("187"),

    /** The get member awards failed. */
    GET_MEMBER_AWARDS_FAILED("190"),

    /** The get member orders failed. */
    GET_MEMBER_ORDERS_FAILED("153"),

    /** The google request error. */
    GOOGLE_REQUEST_ERROR("156"),

    /** The google wallet signature error. */
    GOOGLE_WALLET_SIGNATURE_ERROR("196"),

    /**
     * The service is not available.
     */
    HOTEL_AVAILABILITY_SERVICE_UNAVAILABLE("161"),

    /** The http entity error. */
    HTTP_ENTITY_ERROR("104"),

    /** The http io error. */
    HTTP_IO_ERROR("102"),

    /** The http response error. */
    HTTP_RESPONSE_ERROR("101"),

    /** The hystrix runtime exception. */
    HYSTRIX_RUNTIME_EXCEPTION("139"),

    /** The instant hold cancel job error. */
    INSTAND_HOLD_CANCEL_JOB_ERROR("211"),

    /** The instant hold db error. */
    INSTANT_HOLD_DB_ERROR("205"),

    /** The instant hold warning not possible error. */
    INSTANT_HOLD_WARNING_NOT_POSSIBLE("188"),

    /**
     * The property doesn't support this kind of credit card.
     */
    INVALID_CREDIT_CARD_FOR_PROPERTY("200"),

    /** The invalid rate type. */
    INVALID_RATE_TYPE("100"),

    /** The issue order failed. */
    ISSUE_ORDER_FAILED("155"),

    /**
     * The service is not available.
     */
    ISSUE_ORDER_SERVICE_UNAVAILABLE("175"),

    /** The jaxb error. */
    JAXB_ERROR("105"),

    /** The lanyon parsing. */
    LANYON_PARSING("183"),

    /** The login member failed. */
    LOGIN_MEMBER_FAILED("206"),

    /** The login member service unavailable. */
    LOGIN_MEMBER_SERVICE_UNAVAILABLE("207"),

    /** The loyalty service unavailable. */
    LOYALTY_SERVICE_UNAVAILABLE("213"),

    /** The EZSHIPPecommerce pms service unavailable. */
    EZSHIPPECOMMERCE_SERVICE_UNAVAILABLE("6001"),

    /** The ltv connect service unavailable. */
    LTV_CONNECT_SERVICE_UNAVAILABLE("215"),

    /** The marshalling error. */
    MARSHALLING_ERROR("113"),

    /**
     * The service is not available.
     */
    MBR_ORDERS_SERVICE_UNAVAILABLE("171"),

    /**
     * The service is not available.
     */
    MEMBER_ATTRIBUTES_SERVICE_UNAVAILABLE("172"),

    /** The member awards service unavailable. */
    MEMBER_AWARDS_SERVICE_UNAVAILABLE("191"),

    /** The member set partner registration service unavailable. */
    MEMBER_SET_PARTNER_REGISTRATION_SERVICE_UNAVAILABLE("194"),

    /**
     * The service is not available.
     */
    MEMBER_TRANSACTION_SERVICE_UNAVAILABLE("174"),

    /** The merchant link error. */
    MERCHANT_LINK_ERROR("204"),

    /** The MERCHANT_LINK_MODIFY_SERVICE_UNAVAILABLE. */
    MERCHANT_LINK_MODIFY_SERVICE_UNAVAILABLE("218"),

    /** The Merchant Link Service unavailable. */
    MERCHANT_LINK_SERVICE_UNAVAILABLE("199"),

    /** The modify reservation not available failure. */
    MODIFY_RESERVATION_NOT_AVAILABLE_FAILURE("217"),

    /**
     * The service is not available.
     */
    MODIFY_RESERVATION_SERVICE_UNAVAILABLE("168"),

    /** The msi pms service is not available. */
    MSI_PMS_SERVICE_UNAVAILABLE("212"),

    /** The multiple currency codes. */
    MULTIPLE_CURRENCY_CODES("107"),

    /** The multiple hotel nodes. */
    MULTIPLE_HOTEL_NODES("109"),

    /** The no credit card validator. */
    NO_CREDIT_CARD_VALIDATOR("106"),

    /** The no lanyon files. */
    NO_LANYON_FILES("182"),

    /** The no lanyon hotel details. */
    NO_LANYON_HOTEL_DETAILS("184"),

    /** The no partner order confirmation number. */
    NO_PARTNER_ORDER_CONFIRMATION_NUMBER("223"),

    /** The no property. */
    NO_PROPERTY("108"),

    /** The no returns profile found. */
    NO_RETURNS_PROFILE_FOUND("224"),

    /** The not implemented. */
    NOT_IMPLEMENTED("122"),

    /** The null booking channel. */
    NULL_BOOKING_CHANNEL("145"),

    /** The operation information unavailable. */
    OPERATION_INFORMATION_UNAVAILABLE("158"),

    /** The ota mapping error. */
    OTA_MAPPING_ERROR("1004"),

    /** The pegasus database error. */
    PEGASUS_DATABASE_ERROR("125"),

    /** The pegasus generic error. */
    PEGASUS_GENERIC_ERROR("127"),

    /** The pegasus invalid message originator. */
    PEGASUS_INVALID_MESSAGE_ORIGINATOR("126"),

    /** The pegasus invalid profile information. */
    PEGASUS_INVALID_PROFILE_INFORMATION("1003"),

    /** The PEGASU s_ no n_200. */
    PEGASUS_NON_200("103"),

    /** The pegasus profile service unavailable. */
    PEGASUS_PROFILE_SERVICE_UNAVAILABLE("1116"),

    /** The pegasus property id not found. */
    PEGASUS_PROPERTY_ID_NOT_FOUND("132"),

    /** The pegasus xpath evalution. */
    PEGASUS_XPATH_EVALUTION("149"),

    /** The pegaus response parsing error. */
    PEGAUS_RESPONSE_PARSING_ERROR("150"),

    /** The error reaching ecommerce. */
    PEL_ERROR_REACHING_ECOMMERCE("3000"),

    /** The error reaching notification. */
    PEL_ERROR_REACHING_NOTIFICATION_API("3001"),

    /** The phone parsing. */
    PHONE_PARSING("197"),

    /** The place input order failed. */
    PLACE_ADDRESS_ORDER_FAILED("202"),

    /** The place input order service unavailable. */
    PLACE_ADDRESS_ORDER_SERVICE_UNAVAILABLE("201"),

    /** The place order failed. */
    PLACE_ORDER_FAILED("154"),

    /** The service is not available. */
    PLACE_ORDER_SERVICE_UNAVAILABLE("176"),

    /** The PMS Service unavailable. */
    PMS_SERVICE_UNAVAILABLE("195"),

    /** The price range not found in pegasus response. */
    PRICE_RANGE_NOT_FOUND("181"),

    /** The profile missing from db. */
    PROFILE_MISSING_FROM_DB("208"),

    /** The service is not available. */
    PROFILE_SERVICE_UNAVAILABLE("173"),

    /** The property overwrite constraint violation. */
    PROPERTY_OVERWRITE_CONSTRAINT_VIOLATION("210"),

    /** The rate plan not found in the availability request. */
    RATE_CODE_NOT_FOUND_IN_REQUEST("111"),

    /** The redeem visa service unenroll error. */
    REDEEM_VISA_SERVICE_UNENROLL_ERROR("5000"),

    /** The requested rate plan not found. */
    REQUESTED_RATE_PLAN_NOT_FOUND("110"),

    /** The reservation information unavilable. */
    RESERVATION_INFORMATION_UNAVAILABLE("159"),

    /** The returns db error. */
    RETURNS_DB_ERROR("142"),

    /** The returns id creation error. */
    RETURNS_ID_CREATION_ERROR("129"),

    /**
     * The service is not available.
     */
    REWARDS_SERVICE_UNAVAILABLE("170"),

    /** The service is not available. */
    ROOM_AVAILABILITY_SERVICE_UNAVAILABLE("166"),

    /** The service is not available. */
    ROOM_IS_READY_SERVICE_UNAVAILABLE("178"),

    /** The service is not available. */
    ROOM_RATES_SERVICE_UNAVAILABLE("167"),

    /** The Room Ready Service error code. */
    ROOM_READY_SERVICE_PROBLEM("148"),

    /** The service has an internal error. */
    SERVICE_HAS_AN_INTERNAL_ERROR("180"),

    /** The sftp connection failure. */
    SFTP_CONNECTION_FAILURE("1005"),

    /** The SMS Service unavailable. */
    SMS_SERVIVE_UNAVAILABLE("192"),

    /** The unable to cancel fnc. */
    UNABLE_TO_CANCEL_FNC("157"),

    /** The unable to save reservation. */
    UNABLE_TO_SAVE_RESERVATION("131"),

    /** The unknown reason exception. */
    UNKNOWN_REASON("146"),

    UNMARSHALLING_ERROR("999"),

    INVALID_SESSION("9999"),

    /** The invalid longitude and latitude. */
    INVALID_LONGITUDE_LATITUDE("147"),

    /** The invalid longitude and latitude. */
    NO_PLACES_FOUND("148"),

	/** No user with user id found */
	 NO_USER_FOUND("601"),
	 
	 /** No branch found with branch id */
	BRANCH_NOT_FOUND("602"),
	
	/** Branch admin does not exist */
	BRANCH_ADMIN_NOT_FOUND("603") ,
	
	/** Customer Does Not Exist */
	CUSTOMER_NOT_FOUND("604"),
	
	/** Product Not Exist */
	PRODUCT_NOT_FOUND("605"),
	
	/** Product From Different Branch */
	PRODUCT_FROM_DIFFERENT_BRANCH("606"),
	
	/** Cart Not Deleted */
	CART_NOT_DELETED("607"),
	
	/** User Cart Not Found */
	USER_CART_NOT_FOUND("608"),
	
	/** Customer Address Not Found */
	CUSTOMER_ADDRESS_NOT_FOUND("609"),
	
	/** Atleast One Admin Needed */
	ATLEAST_ONE_ADMIN_FOR_BRANCH_NEEDED("610"),
	
	/** Order Not Matched */
	ORDER_AMOUNT_NOT_MATCHED("611"),
	
	/** Admin Already Exist */
	ADMIN_PHONE_ALREADY_REGISTERED_FOR_BRANCH("612"),

	/** No Orders For Branch*/
	NO_ORDERS_FOR_BRANCH("613"), 
	
	/** User type is not employee */
	USER_TYPE_NOT_EMPLOYEE("614"),
	
	/** Branch product not found */
	 BRANCH_PRODUCT_NOT_FOUND("615")	
	 
	
	 /** The message number. */;
    private final String messageNumber;

    /**
     * Instantiates a new service exception code.
     *
     * @param messageNumber the message number
     */
    private ServiceExceptionCode(String messageNumber) {
        this.messageNumber = messageNumber;
    }

    /**
     * Gets the ServiceExceptionCode for a message number.
     *
     * @param messageNumber the message number
     * @return the ServiceExceptionCode or null if not found
     */
    public static ServiceExceptionCode forMessageNumber(String messageNumber) {
        ServiceExceptionCode code = null;
        if (messageNumber == null) {
            return null;
        }
        for (ServiceExceptionCode serviceExceptionCode : ServiceExceptionCode.values()) {
            if (messageNumber.equals(serviceExceptionCode.getMessageNumber())) {
                code = serviceExceptionCode;
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
