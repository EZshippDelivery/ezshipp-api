package com.ezshipp.api.exception;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The Class ServiceException.
 */
public class ServiceException extends AException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6285761498425736022L;

    /** The error code. */
    private final ServiceExceptionCode code;

    /**
     * Instantiates a new service exception.
     *
     * @param code the code
     */
    public ServiceException(ServiceExceptionCode code) {
        super(code.getMessageNumber());
        this.code = code;
    }

    /**
     * Instantiates a new service exception.
     *
     * @param code the code
     * @param t the t
     */
    public ServiceException(ServiceExceptionCode code, Throwable t) {
        super(code.getMessageNumber(), t);
        this.code = code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.laquinta.service.common.exception.AException#getBody()
     */
    @Override
    public Object getBody() {
        return getErrorMessage();
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public ServiceExceptionCode getCode() {
        return code;
    }

    /**
     * Gets the code message.
     *
     * @return the code message
     */
    public String getCodeMessage() {
        return code.getMessageNumber();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.laquinta.service.common.exception.AException#getErrorCodeDescription()
     */
    @Override
    public String getErrorCodeDescription() {
        return getCode().toString();
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        // grab a resource bundle
        // FIXME add locale - it seems to be getting locale from the server's locale
        ResourceBundle bundle = ResourceBundle.getBundle("exception/exceptions");

        // get the message from the bundle
        String msg = null;
        try {
            msg = bundle.getString("service.exception.message." + getCode().getMessageNumber());

            // MessageFormat will strip out single quotes without this line
            msg = msg.replace("'", "''");
            msg = MessageFormat.format(msg, (Object[]) getCustomFields());
        }
        catch (MissingResourceException e) {
            // if we can't find the specific error message provide a general message
            msg = bundle.getString("service.exception.message");
        }

        return msg;
    }

    /**
     * With params.
     *
     * @param values the values
     * @return the service exception
     */
    public ServiceException withParams(String[] values) {
        customFields = values.clone();
        return this;
    }

}
