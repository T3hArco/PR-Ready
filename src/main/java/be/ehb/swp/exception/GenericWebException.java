package be.ehb.swp.exception;

/**
 * Created by arnaudcoel on 22/10/15.
 */
public class GenericWebException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMsg;

    public GenericWebException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}