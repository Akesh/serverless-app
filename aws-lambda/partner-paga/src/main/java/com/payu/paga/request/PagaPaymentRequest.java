package com.payu.paga.request;

/**
 * Created by akesh.patil on 08-03-2017.
 */

public class PagaPaymentRequest {

    private String authorizationCode;
    private long amount;
    private String currency;
    private String country;
    private long referrenceNumber;
    private String productCode;

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getReferrenceNumber() {
        return referrenceNumber;
    }

    public void setReferrenceNumber(long referrenceNumber) {
        this.referrenceNumber = referrenceNumber;
    }


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

}