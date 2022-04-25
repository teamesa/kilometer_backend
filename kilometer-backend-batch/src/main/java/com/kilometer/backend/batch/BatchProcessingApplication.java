package com.kilometer.backend.batch;

import com.kilometer.domain.cofiguration.DomainConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@Import(DomainConfiguration.class)
@SpringBootApplication
public class BatchProcessingApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(BatchProcessingApplication.class, args);
        System.exit(SpringApplication.exit(ctx));
    }
}
