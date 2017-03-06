import com.amazonaws.services.lambda.runtime.Context;
import com.payu.hello.handler.HelloAppHandler;
import com.payu.hello.request.HelloRequest;
import com.payu.hello.response.HelloResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by akesh.patil on 06-03-2017.
 */
public class HelloAppHandlerTest {
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
        HelloAppHandler handler = new HelloAppHandler();
        Context ctx = createContext();
        HelloRequest request = new HelloRequest();
        request.setFname("Akesh");
        request.setLname("Patil");
        HelloResponse response = handler.handleRequest(request, ctx);
        // TODO: validate output here if needed.
        if (response != null) {
            System.out.println(response.toString());
        }
    }
}
