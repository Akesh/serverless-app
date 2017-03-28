package com.payu.test;

import java.io.IOException;

import com.payu.paga.handler.PartnerPagaToCoreTransactionsHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static DynamodbEvent input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = TestUtils.parse("test1.json", DynamodbEvent.class);
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        PartnerPagaToCoreTransactionsHandler handler = new PartnerPagaToCoreTransactionsHandler();
        Context ctx = createContext();

        Object output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        if (output != null) {
            System.out.println(output.toString());
        }
    }
}
