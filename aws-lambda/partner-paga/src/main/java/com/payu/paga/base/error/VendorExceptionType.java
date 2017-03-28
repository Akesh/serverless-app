package com.payu.paga.base.error;

/**
 * Created by akesh.patil on 15-03-2017.
 */
public enum VendorExceptionType {
    GENERAL_EXCEPTION,
    CONNECTION_FAILURE,
    ILLEGAL_PHONE_NUMBER,
    MESSAGE_PARSING_ERROR,
    SECURITY_VALIDATION_FAILURE,
    BUSINESS_ERROR,
    TIMEOUT,
    INVALID_ACCOUNT,
    INSUFFICIENT_FUND
}
