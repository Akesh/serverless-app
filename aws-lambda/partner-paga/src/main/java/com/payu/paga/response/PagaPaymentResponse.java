package com.payu.paga.response;

import com.payu.paga.type.TransactionStatusType;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

/**
 * Created by akesh.patil on 08-03-2017.
 */
public class PagaPaymentResponse {
    private String transactionId;

    private String responseMessage;

    private String responseCode;

    private String confirmationCode;

    private TransactionStatusType transactionStatus;

    public PagaPaymentResponse(JSONObject merchantPaymentResultJsonObject) throws JSONException {

        JSONObject jsonObject = null;

        Object errorCode = null;

        Object errorMessage = null;

        try {
            jsonObject = merchantPaymentResultJsonObject.getJSONObject("merchantPaymentResult");

            errorCode = jsonObject.isNull("errorCode") ? null : jsonObject
                    .get("errorCode");

            errorMessage = jsonObject.isNull("errorMessage") ? null
                    : jsonObject.get("errorMessage");

            transactionStatus = TransactionStatusType.FAILED;

        } catch (JSONException e) {

            errorCode = "100";

            errorMessage = merchantPaymentResultJsonObject
                    .get("error_description");
        }

        if (StringUtils.isEmpty(errorCode) && StringUtils.isEmpty(errorMessage)) {

            confirmationCode = jsonObject.isNull("confirmationCode") ? null
                    : jsonObject.getString("confirmationCode");

            transactionId = jsonObject.isNull("transactionId") ? null
                    : jsonObject.getString("transactionId");

            transactionStatus = TransactionStatusType.SUCCESS;

        } else {
            responseCode = errorCode.toString();

            responseMessage = errorMessage.toString();
        }
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionStatusType getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusType transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
