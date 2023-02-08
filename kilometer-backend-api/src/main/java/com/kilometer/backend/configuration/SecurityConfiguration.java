package com.kilometer.backend.configuration;

import com.kilometer.backend.security.security.CustomUserDetailsService;
import com.kilometer.backend.security.security.token.TokenAuthenticationFilter;
import com.kilometer.domain.util.ApiUrlUtils;
import java.util.List;
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

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.

      HttpCookieOAuth2AuthorizationReqeustRepository
      - JWT를 사용하기 때문에 Session에 저장할 필요가 없어져, Authorization Request를 Based64 encoded cookie에 저장
    */

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService);
    }

    /*
      AuthenticationManager를 외부에서 사용하기 위해 AuthenticationManager Bean을 통해
      @Autowired가 아닌 @Bean 설정으로 Spring Security 밖으로 Authentication 추출
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // CORS 허용
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                // 토큰을 사용하기 위해 sessionCreationPolicy를 STATELESS로 설정 (Session 비활성화)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                // CSRF 비활성화
                .csrf().disable()
                // 로그인폼 비활성화
                .formLogin().disable()
                // 기본 로그인 창 비활성화
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/", "/test", "/api/authentication").permitAll()
                .antMatchers("/hello-example").permitAll()
                .antMatchers(HttpMethod.GET, ApiUrlUtils.PICK_ROOT + ApiUrlUtils.PICK_MOST).permitAll()
                .antMatchers(HttpMethod.POST, ApiUrlUtils.SEARCH_ROOT).permitAll()
                .antMatchers(ApiUrlUtils.ITEM_ROOT + "/**").permitAll()
                .antMatchers(HttpMethod.GET, ApiUrlUtils.ARCHIVE_ROOT + ApiUrlUtils.ITEM_ID).permitAll()
                .antMatchers(HttpMethod.GET, ApiUrlUtils.HOME_ROOT + "/**").permitAll()
                .antMatchers(HttpMethod.GET, ApiUrlUtils.PICK_MOST).permitAll()
                .antMatchers(HttpMethod.GET, ApiUrlUtils.SEARCH_AUTO_COMPLETE + "/**").permitAll()
                // swagger
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                        "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .anyRequest().authenticated();

        // Add our custom Token based authentication filter
        // UsernamePasswordAuthenticationFilter 앞에 custom 필터 추가!
        http.addFilterBefore(tokenAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of("http://localhost:3000", "http://azxca1731.synology.me:4000",
                        "http://www.kilometer.shop"));
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "OPTIONS"));
        configuration.setAllowedHeaders(
                List.of("Authorization", "Cache-Control", "Content-Type", "X-PINGOTHER"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
