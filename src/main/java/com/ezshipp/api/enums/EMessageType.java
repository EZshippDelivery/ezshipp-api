package com.ezshipp.api.enums;

public enum EMessageType {

    /** The cancel order email. */
    CANCEL_ORDER("CANCEL_ORDER"),

    /** The delivered order email. */
    DELIVER_ORDER("DELIVER_ORDER"),

    /** The customer otp email. */
    CUSTOMER_OTP("CUSTOMER_OTP"),

    /** The reset password email. */
    TEMP_PASSWORD("TEMP_PASSWORD"),

    /** The welcome email. */
    WELCOME("WELCOME"),

    /** The receiver sms. */
    RECEIVER_SMS("RECEIVER_SMS"),

    /** The leave approval email. */
    LEAVE_APPROVAL("LEAVE_APPROVAL");


    /** The name. */
    private String name;

    /**
     * Instantiates a new e reservation message type.
     *
     * @param name the name
     */
    private EMessageType(String name) {
        this.name = name;
    }

    /**
     * For name.
     *
     * @param name the name
     * @return the e reservation message type
     */
    public static EMessageType forName(String name) {
        for (EMessageType type : values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

}
