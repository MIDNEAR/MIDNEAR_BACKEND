package com.midnear.midnearshopping.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface OAuthProvider {
    default String getAccessToken(String code, String state) throws JsonProcessingException {
        return getAccessToken(code);
    }

    String getAccessToken(String code) throws JsonProcessingException;

    Map<String, Object> getUserInfo(String accessToken) throws JsonProcessingException;
}

