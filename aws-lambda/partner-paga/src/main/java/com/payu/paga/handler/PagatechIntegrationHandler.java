package com.payu.paga.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.payu.dynamo.DynamoDBClient;
import com.payu.lambda.BaseRequestHandler;
import com.payu.paga.request.TransactionRequest;
import com.payu.paga.response.TransactionResponse;
import com.payu.paga.type.RequestType;
import com.payu.paga.util.PagatechUtil;
import com.payu.util.HTTPClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;

/**
 * Created by akesh.patil on 08-03-2017.
 */
public class PagatechIntegrationHandler extends BaseRequestHandler<TransactionRequest, TransactionResponse> {

    @Override
    public TransactionResponse handleRequest(TransactionRequest transactionRequest, Context context) {
        if (transactionRequest.getRequestType().equals(RequestType.AUTHORIZAION_CODE_REQUEST)) {
            try {
                OAuthClientRequest oAuthClientRequest = PagatechUtil.getInstance().createOauthClientRequest(transactionRequest, getBean(DynamoDBClient.class));
                return PagatechUtil.getInstance().buildAuthorizationCodeTransactionResponse(oAuthClientRequest);
            } catch (Exception e) {
                context.getLogger().log("Failed to generate auth code url with error " + e);
                return new TransactionResponse(e);
            }
        } else if (transactionRequest.getRequestType().equals(RequestType.PAYMENT_REQUEST)) {
            try {
                return PagatechUtil.getInstance().verifyUserDetailsAndMakePayment(getBean(HTTPClient.class), getBean(DynamoDBClient.class), transactionRequest);
            } catch (Exception e) {
                context.getLogger().log("Failed to make payment with error " + e);
                return new TransactionResponse(e);
            }
        }
        return null;
    }


}
