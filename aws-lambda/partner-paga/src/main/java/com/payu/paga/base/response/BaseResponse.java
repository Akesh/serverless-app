package com.payu.paga.base.response;

import com.payu.paga.base.error.ErrorType;

/**
 * Created by akesh.patil on 14-03-2017.
 */
public class BaseResponse {

    private String errorCode;
    private String errorMessage;
    private ErrorType errorType;

    public BaseResponse() {

    }

    public BaseResponse(Exception e) {
        this.errorMessage = e.getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
