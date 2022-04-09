package com.esa.backend.batch.configuration;

import com.esa.domain.cofiguration.DomainConfiguration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(DomainConfiguration.class)
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
}
