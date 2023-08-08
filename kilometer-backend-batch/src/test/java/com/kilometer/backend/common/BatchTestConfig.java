package com.kilometer.backend.common;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@EntityScan("com.kilometer.domain.crawledItem")
@EnableJpaRepositories("com.kilometer.domain.crawledItem")
@EnableTransactionManagement
public class BatchTestConfig {
}
