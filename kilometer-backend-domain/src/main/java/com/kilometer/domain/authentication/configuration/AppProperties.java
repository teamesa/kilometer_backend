package com.kilometer.domain.authentication.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;
import java.util.List;


@Getter
@Configuration
public class AppProperties {
    @Value("${app.auth.tokenSecret}")
    private String tokenSecret;
    @Value("${app.auth.tokenExpirationMsec}")
    private long tokenExpirationMsec;
}
