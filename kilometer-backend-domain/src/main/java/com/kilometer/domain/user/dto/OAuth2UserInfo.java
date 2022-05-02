package com.kilometer.domain.user.dto;

import com.kilometer.domain.user.Gender;

import java.util.Date;
import java.util.Map;

public abstract  class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract Gender getGender();

    public abstract Date getBirthDate();

    public abstract String getPhoneNumber();
}
