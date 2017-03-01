package com.payu.hello.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.payu.hello.request.HelloRequest;
import com.payu.hello.response.HelloResponse;
import com.payu.lambda.common.BaseRequestHandler;
import com.payu.util.HelloUtil;

import java.util.Map;

/**
 * Created by akesh.patil on 21-02-2017.
 */
public class HelloAppHandler extends BaseRequestHandler<HelloRequest, HelloResponse> {

    public HelloResponse handleRequest(HelloRequest request, Context context) {
        HelloUtil helloUtil = getBean(HelloUtil.class);
        System.out.println("Request Parameters : ");
        System.out.println(request.getKey1() + ":" + request.getKey2() + ":" + request.getKey3());
        HelloResponse helloResponse = new HelloResponse();
        helloResponse.setMessage(helloUtil.getMessage());
        return helloResponse;
    }
}
