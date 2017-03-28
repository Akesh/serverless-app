package com.payu.paga.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.payu.dynamo.DynamoDBClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by akesh.patil on 08-03-2017.
 */

public class PagatechUtil {

    private static final PagatechUtil INSTANCE = new PagatechUtil();

    private static final String PARTNER_ID = "880940b5-bf2a-4dcf-98c0-6c0fb015695b";

    private PagatechUtil() {
    }

    public static PagatechUtil getInstance() {
        return INSTANCE;
    }

    public static final String CORE_TRANSACTIONS_TABLE = "core_transactions";

    private volatile AmazonDynamoDBClient dynamoDBClient = null;

    private volatile DynamoDB dynamoDB = null;

    // Uses Double-checked locking pattern - https://en.wikipedia.org/wiki/Double-checked_locking
    private AmazonDynamoDBClient getAmazonDynamoDBClient(DynamoDBClient dynamoDBClient) {
        // local variable is used to reduce number of accesses to volatile variable (25% performance improvement)
        AmazonDynamoDBClient client = this.dynamoDBClient;
        if (client == null) {
            synchronized (this) {
                client = this.dynamoDBClient;
                if (client == null) {
                    this.dynamoDBClient = client = dynamoDBClient.createDynamoDbClient();
                }
            }
        }
        return client;
    }

    // Uses Double-checked locking pattern - https://en.wikipedia.org/wiki/Double-checked_locking
    private DynamoDB getDynamoDB(DynamoDBClient dynamoDBClient) {
        // local variable is used to reduce number of accesses to volatile variable (25% performance improvement)
        DynamoDB dynamoDB = this.dynamoDB;
        if (dynamoDB == null) {
            synchronized (this) {
                dynamoDB = this.dynamoDB;
                if (dynamoDB == null) {
                    this.dynamoDB = dynamoDB = new DynamoDB(getAmazonDynamoDBClient(dynamoDBClient));
                }
            }
        }
        return dynamoDB;
    }

    public boolean insertPagaTransactionToCoreTransactions(Context context, DynamodbEvent.DynamodbStreamRecord record, DynamoDBClient dynamoDBClient) throws Exception {
        AmazonDynamoDBClient dynamoDB = getAmazonDynamoDBClient(dynamoDBClient);
        Map<String, AttributeValue> item = newItem(record);
        PutItemRequest putItemRequest = new PutItemRequest(CORE_TRANSACTIONS_TABLE, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        context.getLogger().log("Successfully synced transaction to core_transactions table with id " + item.get("id").getS());
        return true;
    }

    private Map<String, AttributeValue> newItem(DynamodbEvent.DynamodbStreamRecord record) {
        StreamRecord sr = record.getDynamodb();
        Map<String, AttributeValue> map = sr.getNewImage();
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("id", new AttributeValue().withS(UUID.randomUUID().toString()));
        item.put("amount", new AttributeValue().withS(map.get("amount").getN()));
        item.put("country", new AttributeValue(map.get("country").getS()));
        item.put("currency", new AttributeValue(map.get("currency").getS()));
        item.put("merchant_id", new AttributeValue().withS(map.get("merchant_id").getS()));
        item.put("partner_id", new AttributeValue().withS(PARTNER_ID));
        item.put("transaction_id", new AttributeValue().withS(map.get("id").getS()));
        //item.put("account", new AttributeValue().withN(String.valueOf(sr.getNewImage().get("account"))));
        item.put("status", new AttributeValue(map.get("transaction_status").getS()));
        item.put("transaction_datetime", new AttributeValue(map.get("transaction_datetime").getS()));
        return item;
    }

    public boolean updatePagaTransactionToCoreTransactions(Context context, DynamodbEvent.DynamodbStreamRecord record, DynamoDBClient dynamoDBClient) throws Exception {
        AmazonDynamoDBClient dynamoClient = getAmazonDynamoDBClient(dynamoDBClient);

        // Get transaction_id(Global secondary index) on core_transactions table
        String transactionId = record.getDynamodb().getOldImage().get("id").getS();
        Map<String, AttributeValue> transactionIdMap = new HashMap<String, AttributeValue>();
        transactionIdMap.put(":trn_id", new AttributeValue().withS(transactionId));

        DynamoDB dynamoDB = getDynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(CORE_TRANSACTIONS_TABLE);
        // Query core transactions table to get partition key of the item using which we can update
        QueryRequest qr = new QueryRequest().withTableName(CORE_TRANSACTIONS_TABLE).
                withIndexName("transaction_id_index").withKeyConditionExpression("transaction_id = :trn_id").
                withExpressionAttributeValues(transactionIdMap);
        QueryResult result = dynamoClient.query(qr);

        // Get the primary/hash key of item from query result
        Map<String, AttributeValue> attributeValueMap = new HashMap<String, AttributeValue>();
        attributeValueMap.put("id", new AttributeValue().withS(result.getItems().get(0).get("id").getS()));

        // Update transaction status into core_transactions
        String transactionStatus = record.getDynamodb().getNewImage().get("transaction_status").getS();
        UpdateItemRequest updateItemRequest = new UpdateItemRequest().withTableName(CORE_TRANSACTIONS_TABLE).withKey(attributeValueMap)
                .addAttributeUpdatesEntry("status", new AttributeValueUpdate().withValue(new AttributeValue().withS(transactionStatus)));
        dynamoClient.updateItem(updateItemRequest);
        context.getLogger().log("Succesfully updated transaction with transaction_id " + transactionId + " to status " + transactionStatus);
        return true;
    }
}
