package com.payu.paga.response;


import com.payu.paga.base.response.BaseResponse;

/**
 * Created by akesh.patil on 09-03-2017.
 */
public class TransactionResponse extends BaseResponse {

    private AuthCodeGenerationResponse authCodeGenerationResponse;
    private AccessTokenInfo accessTokenInfo;
    private PagaPaymentResponse pagaPaymentResponse;

    public TransactionResponse() {
    }

    public TransactionResponse(Exception e) {
        super(e);
    }

    public AccessTokenInfo getAccessTokenInfo() {
        return accessTokenInfo;
    }

    public void setAccessTokenInfo(AccessTokenInfo accessTokenInfo) {
        this.accessTokenInfo = accessTokenInfo;
    }

    public AuthCodeGenerationResponse getAuthCodeGenerationResponse() {
        return authCodeGenerationResponse;
    }

    public void setAuthCodeGenerationResponse(AuthCodeGenerationResponse authCodeGenerationResponse) {
        this.authCodeGenerationResponse = authCodeGenerationResponse;
    }

    public PagaPaymentResponse getPagaPaymentResponse() {
        return pagaPaymentResponse;
    }

    public void setPagaPaymentResponse(PagaPaymentResponse pagaPaymentResponse) {
        this.pagaPaymentResponse = pagaPaymentResponse;
    }
}
