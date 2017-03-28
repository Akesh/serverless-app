import com.amazonaws.services.lambda.runtime.Context;
import com.payu.paga.handler.PagatechIntegrationHandler;
import com.payu.paga.request.AuthCodeGenerationRequest;
import com.payu.paga.request.PagaPaymentRequest;
import com.payu.paga.request.TransactionRequest;
import com.payu.paga.response.TransactionResponse;
import com.payu.paga.type.RequestType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by akesh.patil on 09-03-2017.
 */
public class PagatechIntegrationHandlerTest {
    private static String input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        PagatechIntegrationHandler handler = new PagatechIntegrationHandler();
        Context ctx = createContext();
        TransactionResponse response = handler.handleRequest(createOauthCodeGenerationRequest(), ctx);
        //TransactionResponse response = handler.handleRequest(createAccessTokenGenerationRequest(), ctx);
        System.out.println(response.getAuthCodeGenerationResponse().getAuthCodeGenerationUri());
        // TODO: validate output here if needed.
        if (response != null) {
            System.out.println(response.toString());
        }
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
        pagaPaymentRequest.setAuthorizationCode("DfwKVm");
        request.setTransactionId("72b15c4a-f596-4233-967c-ae30ca059bdb");
        request.setMerchantId("8ebf5c0f-2e68-4801-aa6a-ad9f8f765661");
        request.setPagaPaymentRequest(pagaPaymentRequest);
        return request;
    }
}
