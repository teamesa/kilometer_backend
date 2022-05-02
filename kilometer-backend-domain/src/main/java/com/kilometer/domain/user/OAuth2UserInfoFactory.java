package com.kilometer.domain.user;

import com.kilometer.domain.user.dto.NaverOAuth2UserInfo;
import com.kilometer.domain.user.dto.OAuth2UserInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@SuppressWarnings("unchecked")
public class OAuth2UserInfoFactory {

    public OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.naver.toString())) {
            Object naverAttr = attributes.get("response");
            if (naverAttr instanceof Map) {
                return new NaverOAuth2UserInfo((Map<String, Object>) naverAttr);
            }
        }
        throw new RuntimeException(registrationId + " 로그인은 지원하지 않습니다.");
    }
}
