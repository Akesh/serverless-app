package com.payu.paga.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.payu.dynamo.DynamoDBClient;
import com.payu.lambda.BaseRequestHandler;
import com.payu.paga.type.EventType;
import com.payu.paga.util.PagatechUtil;

import java.util.List;

/**
 * Created by akesh.patil on 24-03-2017.
 */
public class PartnerPagaToCoreTransactionsHandler extends BaseRequestHandler<DynamodbEvent, Boolean> {

    @Override
    public Boolean handleRequest(DynamodbEvent input, Context context) {
        context.getLogger().log("Input: " + input.getRecords().get(0));
        List<DynamodbEvent.DynamodbStreamRecord> list = input.getRecords();
        String eventType = null;
        for (DynamodbEvent.DynamodbStreamRecord record : list) {
            eventType = record.getEventName();
            context.getLogger().log("Action on " + eventType + " event");
            if (EventType.isInsertEvent(eventType)) {
                try {
                    return PagatechUtil.getInstance().insertPagaTransactionToCoreTransactions(context, record, getBean(DynamoDBClient.class));
                } catch (Exception e) {
                    context.getLogger().log("Failed to sync transaction into core_transactions with error " + e);
                }
            } else if (EventType.isUpdateEvent(eventType)) {
                try {
                    return PagatechUtil.getInstance().updatePagaTransactionToCoreTransactions(context, record, getBean(DynamoDBClient.class));
                } catch (Exception e) {
                    context.getLogger().log("Failed to update transaction into core_transactions with error " + e);
                }
            }
        }
        return Boolean.FALSE;
    }
}
