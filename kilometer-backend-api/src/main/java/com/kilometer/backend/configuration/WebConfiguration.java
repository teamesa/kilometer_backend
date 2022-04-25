package com.kilometer.backend.configuration;

import com.kilometer.domain.cofiguration.DomainConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DomainConfiguration.class)
public class WebConfiguration {
}
