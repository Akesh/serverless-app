package com.payu.paga.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.payu.dynamo.DynamoDBClient;
import com.payu.paga.request.PagaPaymentRequest;
import com.payu.paga.request.TransactionRequest;
import com.payu.paga.response.AccessTokenInfo;
import com.payu.paga.response.AuthCodeGenerationResponse;
import com.payu.paga.response.PagaPaymentResponse;
import com.payu.paga.response.TransactionResponse;
import com.payu.paga.type.TransactionStatusType;
import com.payu.util.HTTPClient;
import org.apache.commons.codec.binary.Base64;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akesh.patil on 08-03-2017.
 */

public class PagatechUtil {

    private static final PagatechUtil INSTANCE = new PagatechUtil();

    private PagatechUtil() {
    }

    public static PagatechUtil getInstance() {
        return INSTANCE;
    }

    public static final String OAUTH_TOKEN = "OUAHT";
    public static final String PAYMENT = "PAYMENT";
    public static final String PAGA_PAYMENT_URI = "https://qa1.mypaga.com/paga-webservices/oauth2/secure/merchantPayment";
    public static final String PAGA_AUTHORIZATION_URI = "https://qa1.mypaga.com/paga-webservices/oauth2/authorization";
    //public static final String PAGA_CLIENT_ID = "A3878DC1-F07D-48E7-AA59-8276C3C26647";
    public static final String PAGA_CLIENT_ID = "98F32858-CC3B-42D4-95A3-742110A8D405";
    public static final String PAGA_TOKEN_URI = "https://qa1.mypaga.com/paga-webservices/oauth2/token";
    //public static final String PAGA_CLIENT_SECRET = "*3646f98qu#k";
    public static final String PAGA_CLIENT_SECRET = "rR9@f8u@bBES";
    //public static final String PAGA_SCOPE = "MERCHANT_PAYMENT,USER_DETAILS_REQUEST";
    public static final String PAGA_SCOPE = "MERCHANT_PAYMENT";
    public static final String PAGA_TOKEN_INFO_URI = "https://qa1.mypaga.com/paga-webservices/oauth2/token/info";
    public static final String PAGA_USER_DATA = "ACCOUNT_BALANCE";
    public static final String PAGA_USER_DETAIL_URI = "paga.userdetail.uri";
    public static final String DISPLAY_NAME = "PayU";
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String ACCEPT_POLICY = "application/json";

    public static final String PAGATECH_DYNAMODB_TABLE = "partner_pagatech_transactions";

    public static final String TIMESTAMP_FORMAT = "MM/dd/yyyy hh:mm:ss aa";

    private static final DecimalFormat twoDecimalFormat = new DecimalFormat("#.00");

    private volatile AmazonDynamoDBClient dynamoDB = null;

    public OAuthClientRequest createOauthClientRequest(TransactionRequest transactionRequest, DynamoDBClient dynamoDBClient) throws OAuthSystemException {
        OAuthClientRequest oauthRequest = OAuthClientRequest
                .authorizationLocation(PAGA_AUTHORIZATION_URI)
                //.setParameter("display_name", DISPLAY_NAME)
                //.setParameter("user_data", PAGA_USER_DATA)
                .setClientId(PAGA_CLIENT_ID)
                .setRedirectURI(transactionRequest.getAuthCodeGenerationRequest().getRedirectUri())
                .setResponseType("code")
                .setState(String.valueOf(transactionRequest.getTransactionId()))
                .setScope(PAGA_SCOPE)
                .buildQueryMessage();
        return oauthRequest;
    }

    public AccessTokenInfo createAccessTokenGenerationRequest(TransactionRequest transactionRequest) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest oauthRequest = null;
        OAuthJSONAccessTokenResponse oAuthResponse = null;
        OAuthClientRequest oAuthClientRequest = null;
        AccessTokenInfo accessTokenInfo = null;
        oAuthClientRequest = OAuthClientRequest.tokenLocation(PagatechUtil.PAGA_TOKEN_URI)
                //.setParameter("display_name", PagatechUtil.DISPLAY_NAME)
                //.setParameter("user_data", PagatechUtil.PAGA_USER_DATA)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setScope(PagatechUtil.PAGA_SCOPE)
                .setRedirectURI("http://34.251.158.154/authClient/authResponse.do")
                .setCode(transactionRequest.getPagaPaymentRequest().getAuthorizationCode()).buildBodyMessage();

        oAuthClientRequest.setHeader("Authorization", "Basic " + Base64.encodeBase64String((PagatechUtil.PAGA_CLIENT_ID + ":" + PagatechUtil.PAGA_CLIENT_SECRET).getBytes()));

        // create OAuth client that uses custom http client under the hood
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        oAuthResponse = oAuthClient.accessToken(oAuthClientRequest, OAuthJSONAccessTokenResponse.class);
        System.out.println("############" + oAuthResponse.getAccessToken());
        accessTokenInfo = new AccessTokenInfo(oAuthResponse.getAccessToken(),
                oAuthResponse.getAccessToken(), oAuthResponse.getParam("limitMaximum"), oAuthResponse.getParam("token_type"));
        // TODO Store details in dynamoDB
        return accessTokenInfo;
    }

    public TransactionResponse buildAuthorizationCodeTransactionResponse(OAuthClientRequest oAuthClientRequest) {
        TransactionResponse response = new TransactionResponse();
        AuthCodeGenerationResponse authCodeGenerationResponse = new AuthCodeGenerationResponse();
        authCodeGenerationResponse.setAuthCodeGenerationUri(oAuthClientRequest.getLocationUri());
        response.setAuthCodeGenerationResponse(authCodeGenerationResponse);
        return response;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat(TIMESTAMP_FORMAT);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public TransactionResponse verifyUserDetailsAndMakePayment(HTTPClient httpClient, DynamoDBClient dynamoDBClient, TransactionRequest transactionRequest) throws Exception {
        HttpsURLConnection httpsURLConnection = null;
        String responseText = "";
        long responseCode = 0L;
        String responseMessage = null;
        AccessTokenInfo accessTokenInfo = createAccessTokenGenerationRequest(transactionRequest);
        //AccessTokenInfo accessTokenInfo = new AccessTokenInfo("0954b479-982d-415d-860e-eaedfd9779b1", null, null, null);
        createDynamoTable(dynamoDBClient);
        addTransactionalRecord(dynamoDBClient, transactionRequest);
        PagaPaymentRequest pagaPaymentRequest = transactionRequest.getPagaPaymentRequest();
        StringBuilder sb = new StringBuilder("referenceNumber=").append(transactionRequest.getTransactionId()).append("&amount=")
                .append(twoDecimalFormat.format(pagaPaymentRequest.getAmount())).append("&merchantCustomerReference=")
                .append(pagaPaymentRequest.getAuthorizationCode());
        //.append("&merchantProductCode=").append(pagaPaymentRequest.getProductCode())
        //.append("&currency=").append(pagaPaymentRequest.getCurrency());
        httpsURLConnection = httpClient.pagaHttpsPost(PAGA_PAYMENT_URI, CONTENT_TYPE, ACCEPT_POLICY, accessTokenInfo.getAccessToken(), sb.toString());
        responseCode = httpsURLConnection.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + PAGA_PAYMENT_URI);
        System.out.println("Post parameters : " + sb.toString());
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        responseText = response.toString();
        JSONObject jsonObject = new JSONObject(responseText);
        PagaPaymentResponse pagaPaymentResponse = new PagaPaymentResponse(jsonObject);
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAccessTokenInfo(accessTokenInfo);
        transactionResponse.setPagaPaymentResponse(pagaPaymentResponse);
        updateTransactionData(dynamoDBClient, transactionRequest, transactionResponse);
        return transactionResponse;
    }

    private void updateTransactionData(DynamoDBClient dynamoDBClient, TransactionRequest transactionRequest, TransactionResponse transactionResponse) throws Exception {
        AmazonDynamoDBClient dynamoDB = getDynamoDB(dynamoDBClient);
        Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
        attributeValues.put("id", new AttributeValue().withS(transactionRequest.getTransactionId()));

        UpdateItemRequest updateItemRequest = new UpdateItemRequest().withTableName(PAGATECH_DYNAMODB_TABLE).withKey(attributeValues);
        if (transactionResponse.getPagaPaymentResponse().getResponseCode() == null && transactionResponse.getPagaPaymentResponse().getResponseMessage() == null) {
            updateItemRequest.addAttributeUpdatesEntry("vendor_transaction_id",
                    new AttributeValueUpdate().withValue(new AttributeValue().withS(transactionResponse.getPagaPaymentResponse().getTransactionId()))).
                    addAttributeUpdatesEntry("transaction_status",
                            new AttributeValueUpdate().withValue(new AttributeValue().withS(TransactionStatusType.SUCCESS.toString())));
        } else {
            updateItemRequest.addAttributeUpdatesEntry("transaction_status",
                    new AttributeValueUpdate().withValue(new AttributeValue().withS(TransactionStatusType.FAILED.toString())));
        }

        UpdateItemResult updateItemResult = dynamoDB.updateItem(updateItemRequest);
    }

    // Uses Double-checked locking pattern - https://en.wikipedia.org/wiki/Double-checked_locking
    private AmazonDynamoDBClient getDynamoDB(DynamoDBClient dynamoDBClient) {
        // local variable is used to reduce number of accesses to volatile variable (25% performance improvement)
        AmazonDynamoDBClient client = dynamoDB;
        if (client == null) {
            synchronized (this) {
                client = dynamoDB;
                if (client == null) {
                    dynamoDB = client = dynamoDBClient.createDynamoDbClient();
                }
            }
        }
        return client;
    }

    public void addTransactionalRecord(DynamoDBClient dynamoDBClient, TransactionRequest transactionRequest) throws Exception {
        AmazonDynamoDBClient dynamoDB = getDynamoDB(dynamoDBClient);
        PagaPaymentRequest pagaPaymentRequest = transactionRequest.getPagaPaymentRequest();
        // Add an item
        Map<String, AttributeValue> item = newItem(transactionRequest.getTransactionId(), transactionRequest.getMerchantId(), pagaPaymentRequest.getReferrenceNumber(), pagaPaymentRequest.getCountry(), pagaPaymentRequest.getAmount(), pagaPaymentRequest.getCurrency(), TransactionStatusType.PENDING.toString(), null, getCurrentTimeStamp());
        PutItemRequest putItemRequest = new PutItemRequest(PAGATECH_DYNAMODB_TABLE, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        System.out.println("Result: " + putItemResult);
    }

    public void createDynamoTable(DynamoDBClient dynamoDBClient) throws Exception {
        AmazonDynamoDBClient dynamoDB = getDynamoDB(dynamoDBClient);
        try {
            // Create a table with a primary hash key named 'name', which holds a string
            CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(PAGATECH_DYNAMODB_TABLE)
                    .withKeySchema(new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition().withAttributeName("id").withAttributeType(ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));

            // Create table if it does not exist yet
            TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
            // wait for the table to move into ACTIVE state
            TableUtils.waitUntilActive(dynamoDB, PAGATECH_DYNAMODB_TABLE);

            // Describe our new table
            DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(PAGATECH_DYNAMODB_TABLE);
            TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    private static Map<String, AttributeValue> newItem(String id, String merchantId, long referrenceNumber, String country, long amount, String currency, String transactionStatus, String vendorTransactionId, String currentTimeStamp) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("id", new AttributeValue().withS(id));
        item.put("merchant_id", new AttributeValue().withS(merchantId));
        item.put("account", new AttributeValue().withN(String.valueOf(referrenceNumber)));
        item.put("country", new AttributeValue(country));
        item.put("amount", new AttributeValue().withN(String.valueOf(amount)));
        item.put("currency", new AttributeValue(currency));
        item.put("transaction_status", new AttributeValue(transactionStatus));
        item.put("transaction_datetime", new AttributeValue(currentTimeStamp));
        //item.put("VendorTransactionId", new AttributeValue(vendorTransactionId));
        return item;
    }
}
