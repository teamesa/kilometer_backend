package com.esa.domain.user.dto;

import com.esa.domain.user.Gender;
import org.springframework.format.datetime.DateFormatter;

import java.util.Date;
import java.util.Locale;
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
    public Date getBirthDate() {
        String birthDay = (String) attributes.get("birthday");
        String birthYear = (String) attributes.get("birthyear");
        DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd");
        try {
            return dateFormatter.parse(String.format("%s-%s", birthYear, birthDay), Locale.getDefault());
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public String getPhoneNumber() {
        return (String) attributes.get("mobile");
    }

}
