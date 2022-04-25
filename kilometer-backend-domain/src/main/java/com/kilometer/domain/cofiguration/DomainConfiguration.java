package com.kilometer.domain.cofiguration;

import com.kilometer.domain.Domains;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Domains.class)
public class DomainConfiguration {

}
