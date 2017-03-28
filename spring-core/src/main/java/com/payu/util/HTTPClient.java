package com.payu.util;

import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.net.URL;

/**
 * Created by akesh.patil on 14-03-2017.
 */

@Component
public class HTTPClient {

    public HttpsURLConnection pagaHttpsPost(String url, String contentType, String accept, String accessToken, String urlParameters) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", contentType); // application/x-www-form-urlencoded
        con.setRequestProperty("Authorization", "Bearer" + accessToken);
        con.setRequestProperty("Accept", accept); // application/json

        //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        con.setUseCaches(false);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        return con;

    }
}
