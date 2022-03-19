package com.esa.backend.configuration;

import com.esa.domain.Domains;
import com.esa.domain.cofiguration.DomainConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DomainConfiguration.class)
@ComponentScan(basePackageClasses = Domains.class)
public class WebConfiguration {
}
