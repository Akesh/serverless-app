package com.payu.hello.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.payu.hello.request.HelloRequest;
import com.payu.hello.response.HelloResponse;
import com.payu.lambda.common.BaseRequestHandler;
import com.payu.util.HelloUtil;

/**
 * Created by akesh.patil on 21-02-2017.
 */
public class HelloAppHandler extends BaseRequestHandler<HelloRequest, HelloResponse> {

    public HelloResponse handleRequest(HelloRequest request, Context context) {
        // This is to get spring initiated object of a class
        HelloUtil helloUtil = getBean(HelloUtil.class);
        HelloResponse helloResponse = new HelloResponse();
        helloResponse.setMessage(helloUtil.getMessage() + " " + request.getFname() + " " + request.getLname());
        return helloResponse;
    }
}
