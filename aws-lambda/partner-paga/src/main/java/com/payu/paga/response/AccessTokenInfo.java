package com.payu.paga.response;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

/**
 * Created by akesh.patil on 15-03-2017.
 */
public class AccessTokenInfo {
    private String accessToken;
    private String refreshToken;
    private String limitMaximum;
    private String tokenType;

    public AccessTokenInfo(String accessToken, String refreshToken, String limitMaximum, String tokenType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.limitMaximum = limitMaximum;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getLimitMaximum() {
        return limitMaximum;
    }

    public void setLimitMaximum(String limitMaximum) {
        this.limitMaximum = limitMaximum;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return this.accessToken + ":" + this.refreshToken + ":" + this.tokenType + ":" + this.limitMaximum;
    }
}
