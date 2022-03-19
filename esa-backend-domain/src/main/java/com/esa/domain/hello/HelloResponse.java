package com.esa.domain.hello;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class HelloResponse {
    private Long id;
    private String service;
    private String memo;
    private LocalDateTime createdAt;
}
