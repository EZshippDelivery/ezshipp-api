package com.ezshipp.api.exception;

/**
 * The Class AException.
 */
public abstract class AException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4158116862036436019L;

    /** The exception body. */
    protected Object body;

    /** The custom fields. */
    // CHECKSTYLE:OFF
    protected String[] customFields;

    // CHECKSTYLE:ON

    /**
     * Instantiates a new a exception.
     *
     * @param messageNumber the message number
     */
    public AException(String messageNumber) {
        super(messageNumber);
    }

    /**
     * Instantiates a new a exception.
     *
     * @param messageNumber the message number
     * @param t the t
     */
    public AException(String messageNumber, Throwable t) {
        super(messageNumber, t);
    }

    /**
     * Gets the exception body.
     *
     * @return the body
     */
    public Object getBody() {
        return body;
    }

    /**
     * Gets the custom fields.
     *
     * @return the custom fields
     */
    public String[] getCustomFields() {
        String[] returnValue = null;
        if (customFields != null) {
            returnValue = customFields.clone();
        }
        return returnValue;
    }

    /**
     * Returns the error code list description, from the name of the constants from BusinessExceptionCode or ServiceExceptionCode. If it is a list of codes, a CVS list is returned.
     *
     * @return the code description.
     * */
    public abstract String getErrorCodeDescription();

    /**
     * Sets the body.
     *
     * @param body the new body
     */
    public void setBody(Object body) {
        this.body = body;
    }

}
