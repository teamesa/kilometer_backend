package com.kilometer.backend.security.security.token;

import com.kilometer.backend.common.Fixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spock.lang.Specification;

@SpringBootTest
class TokenProviderTest extends Specification {

    @Autowired
    private TokenProvider jwtProvider

    def "TokenProvider creates token"() {
        when:
        String token = jwtProvider.createToken(Fixture.USER_RESPONSE)
        then:
        token != null
    }

    def "TokenProvider gets user id from token"() {
        given:
        String token = jwtProvider.createToken(Fixture.USER_RESPONSE)
        when:
        def userId = jwtProvider.getUserIdFromToken(token);
        then:
        userId == Fixture.USER_RESPONSE.getId()
    }

    def "TokenProvider validates token"() {
        given:
        def token = jwtProvider.createToken(Fixture.USER_RESPONSE)
        when:
        def result = jwtProvider.validateToken(token)
        then:
        result
    }
}
