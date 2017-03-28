package com.payu.paga.request;

import com.payu.paga.base.request.BaseRequest;
import com.payu.paga.type.RequestType;

/**
 * Created by akesh.patil on 09-03-2017.
 */
public class TransactionRequest extends BaseRequest {

    private RequestType requestType;
    private AuthCodeGenerationRequest authCodeGenerationRequest;
    private PagaPaymentRequest pagaPaymentRequest;
    private String transactionId;
    private String merchantId;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public AuthCodeGenerationRequest getAuthCodeGenerationRequest() {
        return authCodeGenerationRequest;
    }

    public void setAuthCodeGenerationRequest(AuthCodeGenerationRequest authCodeGenerationRequest) {
        this.authCodeGenerationRequest = authCodeGenerationRequest;
    }

    public PagaPaymentRequest getPagaPaymentRequest() {
        return pagaPaymentRequest;
    }

    public void setPagaPaymentRequest(PagaPaymentRequest pagaPaymentRequest) {
        this.pagaPaymentRequest = pagaPaymentRequest;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
