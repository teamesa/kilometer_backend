package com.kilometer.backend.service.authentication

import com.kilometer.backend.common.Fixture
import com.kilometer.backend.security.security.token.JwtProvider
import com.kilometer.backend.security.security.token.TokenProvider
import com.kilometer.domain.user.UserService
import com.kilometer.domain.user.dto.AuthRequest
import com.kilometer.domain.user.dto.UserResponse
import spock.lang.Specification
import spock.lang.Subject

class AuthenticationServiceTest extends Specification {

    @Subject
    AuthenticationService authenticationService

    TokenProvider tokenProvider = Mock(TokenProvider.class)
    UserService userService = Mock(UserService.class)

    def setup() {
        authenticationService = new AuthenticationService(tokenProvider, userService)
    }

    def "If user joins for the first time, AuthenticationService must save the user and generate token"() {
        given:
        1 * userService.findUserByProviderAndProviderId(_ as AuthRequest) >> Optional.empty()
        1 * userService.saveUser(_ as AuthRequest) >> Fixture.USER_RESPONSE
        1 * tokenProvider.createToken(_ as UserResponse) >> "token"
        when:
        def result = authenticationService.authenticate(Fixture.AUTH_REQUEST)
        then:
        result.accessToken.length() > 0
        result.firstJoined
    }

    def "If user already joined, AuthenticationService must generate token"() {
        given:
        1 * userService.findUserByProviderAndProviderId(_ as AuthRequest) >> Optional.of(Fixture.USER_RESPONSE)
        0 * userService.saveUser(_ as AuthRequest) >> Fixture.USER_RESPONSE
        1 * tokenProvider.createToken(_ as UserResponse) >> "token"
        when:
        def result = authenticationService.authenticate(Fixture.AUTH_REQUEST)
        then:
        result.accessToken.length() > 0
        !result.firstJoined
    }
}
