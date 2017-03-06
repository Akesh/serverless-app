import com.amazonaws.services.lambda.runtime.Context;
import com.payu.bye.handler.ByeAppHandler;
import com.payu.bye.request.ByeRequest;
import com.payu.bye.response.ByeResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by akesh.patil on 06-03-2017.
 */
public class ByeAppLambdaHandlerTest {

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
        ByeAppHandler handler = new ByeAppHandler();
        Context ctx = createContext();
        ByeRequest request = new ByeRequest();
        request.setFname("Akesh");
        request.setLname("Patil");
        ByeResponse response = handler.handleRequest(request, ctx);
        // TODO: validate output here if needed.
        if (response != null) {
            System.out.println(response.toString());
        }
    }

}
