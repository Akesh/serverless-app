package com.payu.bye.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.payu.bye.request.ByeRequest;
import com.payu.bye.response.ByeResponse;
import com.payu.db.model.User;
import com.payu.lambda.common.BaseRequestHandler;
import com.payu.services.UserService;
import com.payu.util.ByeUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by akesh.patil on 21-02-2017.
 */
public class ByeAppHandler extends BaseRequestHandler<ByeRequest, ByeResponse> {


    public ByeResponse handleRequest(ByeRequest byeRequest, Context context) {
        // This is to get spring initiated object of a class
        ByeUtil byeUtil = getBean(ByeUtil.class);
        ByeResponse response = new ByeResponse();
        response.setMessage(byeUtil.getMessage() + " " + byeRequest.getFname() + " " + byeRequest.getLname());
        UserService userService = getBean(UserService.class);
        List<User> users = userService.getAllUsers();
        for (User u : users) {
            System.out.println(u);
        }
        return response;
    }
}