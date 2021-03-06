package com.kilometer.backend.configuration;

import com.kilometer.backend.security.security.CustomUserDetailsService;
import com.kilometer.backend.security.security.token.TokenAuthenticationFilter;
import com.kilometer.backend.security.security.oauth2.CustomOAuth2UserService;
import com.kilometer.backend.security.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.kilometer.backend.security.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.kilometer.backend.security.security.oauth2.OAuth2AuthenticationSuccessHandler;

import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.

      HttpCookieOAuth2AuthorizationReqeustRepository
      - JWT??? ???????????? ????????? Session??? ????????? ????????? ?????????, Authorization Request??? Based64 encoded cookie??? ??????
    */

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService);
    }

    /*
      AuthenticationManager??? ???????????? ???????????? ?????? AuthenticationManager Bean??? ??????
      @Autowired??? ?????? @Bean ???????????? Spring Security ????????? Authentication ??????
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // CORS ??????
                .cors()
                    .configurationSource(corsConfigurationSource())
                .and()
                // ????????? ???????????? ?????? sessionCreationPolicy??? STATELESS??? ?????? (Session ????????????)
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                // CSRF ????????????
                    .csrf().disable()
                // ???????????? ????????????
                    .formLogin().disable()
                // ?????? ????????? ??? ????????????
                    .httpBasic().disable()
                    .authorizeRequests()
                        .antMatchers("/", "/test").permitAll()
                        .antMatchers("/hello-example").permitAll()
                        .antMatchers(HttpMethod.POST,ApiUrlUtils.SEARCH_ROOT).permitAll()
                        .antMatchers(ApiUrlUtils.ITEM_ROOT+"/**").permitAll()
                        .antMatchers(HttpMethod.GET, ApiUrlUtils.ARCHIVE_ROOT + ApiUrlUtils.ITEM_ID).permitAll()
                        .antMatchers(HttpMethod.GET,ApiUrlUtils.SEARCH_ROOT+ApiUrlUtils.SEARCH_AUTO_COMPLETE).permitAll()
                        // swagger
                        .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                        .authorizationEndpoint()
                // ??????????????? ?????? ????????? ?????? URI
                        .baseUri("/oauth2/authorization")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        // Add our custom Token based authentication filter
        // UsernamePasswordAuthenticationFilter ?????? custom ?????? ??????!
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://azxca1731.synology.me:4000", "http://www.kilometer.shop"));
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "X-PINGOTHER"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
