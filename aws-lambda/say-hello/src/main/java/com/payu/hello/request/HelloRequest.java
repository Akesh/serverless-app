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
        "fname",
        "lname"
})
public class HelloRequest {

    @JsonProperty("fname")
    private String fname;
    @JsonProperty("lname")
    private String lname;

    @JsonProperty("fname")
    public String getFname() {
        return fname;
    }

    @JsonProperty("fname")
    public void setFname(String fname) {
        this.fname = fname;
    }

    @JsonProperty("lname")
    public String getLname() {
        return lname;
    }

    @JsonProperty("lname")
    public void setLname(String lname) {
        this.lname = lname;
    }
}