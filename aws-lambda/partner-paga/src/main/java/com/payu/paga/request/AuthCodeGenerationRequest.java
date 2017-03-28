package com.payu.paga.request;

/**
 * Created by akesh.patil on 09-03-2017.
 */
public class AuthCodeGenerationRequest {
    private String redirectUri;

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
