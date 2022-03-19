package com.esa.backend.batch.configuration;

import com.esa.domain.cofiguration.DomainConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(DomainConfiguration.class)
@Configuration
public class BatchConfiguration {
}
