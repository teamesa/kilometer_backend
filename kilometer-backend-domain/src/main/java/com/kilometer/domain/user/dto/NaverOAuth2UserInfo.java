package com.kilometer.domain.user.dto;

import com.kilometer.domain.user.Gender;

import java.time.LocalDateTime;
import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("profile_image");
    }

    @Override
    public Gender getGender() {
        String genderString = (String) attributes.get("gender");
        if ("F".equals(genderString)) {
            return Gender.FEMALE;
        } else if ("M".equals(genderString)) {
            return Gender.MALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

    @Override
    public LocalDateTime getBirthDate() {
        String birthDay = (String) attributes.get("birthday");
        String birthYear = (String) attributes.get("birthyear");
        try {
            return LocalDateTime.parse(String.format("%s-%s", birthYear, birthDay));
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public String getPhoneNumber() {
        return (String) attributes.get("mobile");
    }

}
