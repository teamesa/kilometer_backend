package com.kilometer.backend.security.security.oauth2;

import com.kilometer.backend.security.exception.BadRequestException;
import com.kilometer.backend.security.security.UserPrincipal;
import com.kilometer.backend.security.security.token.TokenProvider;
import com.kilometer.backend.security.util.CookieUtils;
import com.kilometer.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.kilometer.backend.security.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋되었습니다. " + targetUrl + "로 리다이렉션을 할 수 없습니다");
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("승인되지 않은 리디렉션 URI가 있어 인증을 진행할 수 없습니다.");
        }

        String token = tokenProvider.createToken(authentication);

        String targetUrl = makeTargetUrl(redirectUri, authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    private String makeTargetUrl(Optional<String> redirectUri, Authentication authentication) {
        String first = redirectUri.orElse(getDefaultTargetUrl());

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        boolean isFirstUser = Optional.ofNullable(userPrincipal.getUser())
                .map(UserResponse::isCreated)
                .orElse(false);

        if (isFirstUser) {
            return first.substring(0,first.indexOf("redirect_uri")).concat("redirect_uri=/welcome");
        } else {
            return first;
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        return "localhost".equalsIgnoreCase(clientRedirectUri.getHost())
                || "kilometer.shop".equalsIgnoreCase(clientRedirectUri.getHost())
                || "azxca1731.synology.me".equalsIgnoreCase(clientRedirectUri.getHost());
    }
}