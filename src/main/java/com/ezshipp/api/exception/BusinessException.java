package com.ezshipp.api.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The Class BusinessException.
 */
public class BusinessException extends AException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1330182961059538707L;

    /** The error code. */
    private final BusinessExceptionCode code;

    /**
     * Instantiates a new business exception.
     *
     * @param code the error code from BusinessExceptionCode
     */
    public BusinessException(BusinessExceptionCode code) {
        super(code.getMessageNumber());
        this.code = code;
    }

    /**
     * Instantiates a new invalid data exception with params.
     *
     * @param code the business exception code
     * @param params the custom params
     */
    public BusinessException(BusinessExceptionCode code, String[] params) {
        super(code.getMessageNumber());
        customFields = params.clone();
        this.code = code;
    }

    /**
     * Instantiates a new business exception.
     *
     * @param code the code
     * @param t the t
     */
    public BusinessException(BusinessExceptionCode code, Throwable t) {
        super(code.getMessageNumber(), t);
        this.code = code;
    }

    /**
     * Instantiates a new business exception.
     *
     * @param code the code
     * @param t the t
     * @param params the params
     */
    public BusinessException(BusinessExceptionCode code, Throwable t, String[] params) {
        super(code.getMessageNumber(), t);
        customFields = params.clone();
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
     * Return the error code of this exception.
     *
     * @return the error code
     * */
    public BusinessExceptionCode getCode() {
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
            msg = bundle.getString("business.exception.message." + getMessage());

            // MessageFormat will strip out single quotes without this line
            msg = msg.replace("'", "''");
            msg = MessageFormat.format(msg, (Object[]) getCustomFields());
        }
        catch (MissingResourceException e) {
            // if we can't find the specific error message provide a general message
            msg = bundle.getString("business.exception.message");
        }
        return msg;
    }

    /**
     * Get the error list.
     *
     * @return the list of business exception errors
     */
    public List<BusinessException> getExceptionList() {
        List<BusinessException> errorList = new ArrayList<BusinessException>();
        errorList.add(this);
        return errorList;
    }
}
