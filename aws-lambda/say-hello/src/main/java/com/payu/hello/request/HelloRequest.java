package com.payu.hello.request;

/**
 * Created by akesh.patil on 22-02-2017.
 */


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "key3",
        "key2",
        "key1"
})
public class HelloRequest {

    @JsonProperty("key3")
    private String key3;
    @JsonProperty("key2")
    private String key2;
    @JsonProperty("key1")
    private String key1;

    @JsonProperty("key3")
    public String getKey3() {
        return key3;
    }

    @JsonProperty("key3")
    public void setKey3(String key3) {
        this.key3 = key3;
    }

    @JsonProperty("key2")
    public String getKey2() {
        return key2;
    }

    @JsonProperty("key2")
    public void setKey2(String key2) {
        this.key2 = key2;
    }

    @JsonProperty("key1")
    public String getKey1() {
        return key1;
    }

    @JsonProperty("key1")
    public void setKey1(String key1) {
        this.key1 = key1;
    }

}