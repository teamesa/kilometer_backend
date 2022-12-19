package com.kilometer.backend.controller

import org.springframework.http.MediaType

import static org.mockito.BDDMockito.given
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper
import com.kilometer.backend.common.Fixture
import com.kilometer.backend.security.security.CustomUserDetailsService
import com.kilometer.backend.security.security.token.TokenProvider
import com.kilometer.backend.service.authentication.AuthenticationService
import com.kilometer.domain.user.dto.AuthRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@WebMvcTest(controllers = [AuthenticationController])
class AuthenticationControllerTest extends Specification {

    @Autowired
    AuthenticationController authenticationController

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @MockBean
    AuthenticationService authenticationService

    @MockBean
    CustomUserDetailsService customUserDetailsService

    @MockBean TokenProvider tokenProvider

    def "If user successfully requets join, return 200 OK"() {
        given:
        Map requestBody = [
                providerId  : "1",
                provider    : "naver",
                email       : "example@naver.com",
                profileImage: "/img.png",
                gender      : "M",
                birthday    : "03-28",
                birthYear   : "1999",
                phoneNumber : "01012345678"
        ]
        given(authenticationService.authenticate(any(AuthRequest.class))).willReturn(Fixture.AUTHE_RESPONSE)
        when:
        def response = mvc.perform(post('/api/authentication')
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
        then:
        response.andDo(print())
        response.andExpect(status().isOk())
        response.andExpect(content().string(objectMapper.writeValueAsString(Fixture.AUTHE_RESPONSE)))
    }
}
