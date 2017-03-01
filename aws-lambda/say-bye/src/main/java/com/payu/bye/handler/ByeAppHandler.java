package com.payu.bye.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.payu.lambda.common.BaseRequestHandler;
import com.payu.util.ByeUtil;

import java.util.Map;

/**
 * Created by akesh.patil on 21-02-2017.
 */
public class ByeAppHandler extends BaseRequestHandler<Map<String, Object>, String> {

    public String handleRequest(Map<String, Object> input, Context context) {
        ByeUtil byeUtil = getBean(ByeUtil.class);
        return byeUtil.getMessage();
    }
}