package com.esa.backend.configuration;

import com.esa.domain.cofiguration.DomainConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DomainConfiguration.class)
public class BackofficeConfiguration {
}
