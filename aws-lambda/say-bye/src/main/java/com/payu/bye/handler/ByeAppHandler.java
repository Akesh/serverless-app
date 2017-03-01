package com.payu.bye.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.payu.bye.request.ByeRequest;
import com.payu.bye.response.ByeResponse;
import com.payu.lambda.common.BaseRequestHandler;
import com.payu.util.ByeUtil;

/**
 * Created by akesh.patil on 21-02-2017.
 */
public class ByeAppHandler extends BaseRequestHandler<ByeRequest, ByeResponse> {


    public ByeResponse handleRequest(ByeRequest byeRequest, Context context) {
        // This is to get spring initiated object of a class
        ByeUtil byeUtil = getBean(ByeUtil.class);
        ByeResponse response = new ByeResponse();
        response.setMessage(byeUtil.getMessage() + " " + byeRequest.getFname() + " " + byeRequest.getLname());
        return response;
    }
}