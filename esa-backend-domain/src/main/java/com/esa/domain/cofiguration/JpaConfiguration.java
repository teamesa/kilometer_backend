package com.esa.domain.cofiguration;

import com.esa.domain.Domains;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = Domains.class)
@EnableJpaRepositories(basePackageClasses = Domains.class)
public class JpaConfiguration {
}
