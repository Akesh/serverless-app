package com.payu.paga.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payu.paga.request.AuthCodeGenerationRequest;
import com.payu.paga.request.PagaPaymentRequest;
import com.payu.paga.request.TransactionRequest;
import com.payu.paga.type.RequestType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by akesh.patil on 09-03-2017.
 */
public class PojoToJson {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //System.out.println(mapper.writeValueAsString(createOauthCodeGenerationRequest()));
            System.out.println(UUID.randomUUID());
            //System.out.println(mapper.writeValueAsString(createAccessTokenGenerationRequest()));
            //System.out.println(getCurrentTimeStamp());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    private static TransactionRequest createOauthCodeGenerationRequest() {
        TransactionRequest request = new TransactionRequest();
        request.setRequestType(RequestType.AUTHORIZAION_CODE_REQUEST);
        AuthCodeGenerationRequest authCodeGenerationRequest = new AuthCodeGenerationRequest();
        authCodeGenerationRequest.setRedirectUri("http://34.251.158.154/authClient/authResponse.do");
        request.setTransactionId("72b15c4a-f596-4233-967c-ae30ca059bdb");
        //request.setMerchantId("8ebf5c0f-2e68-4801-aa6a-ad9f8f765661");
        request.setAuthCodeGenerationRequest(authCodeGenerationRequest);
        return request;
    }

    private static TransactionRequest createAccessTokenGenerationRequest() {
        TransactionRequest request = new TransactionRequest();
        request.setRequestType(RequestType.PAYMENT_REQUEST);
        PagaPaymentRequest pagaPaymentRequest = new PagaPaymentRequest();
        pagaPaymentRequest.setReferrenceNumber(52775286);
        pagaPaymentRequest.setAmount(22);
        pagaPaymentRequest.setCountry("Nigeria");
        pagaPaymentRequest.setCurrency("NGN");
        pagaPaymentRequest.setAuthorizationCode("3PxSzI");
        request.setTransactionId("72b15c4a-f596-4233-967c-ae30ca059bdb");
        request.setMerchantId("8ebf5c0f-2e68-4801-aa6a-ad9f8f765661");
        request.setPagaPaymentRequest(pagaPaymentRequest);
        return request;
    }
}
