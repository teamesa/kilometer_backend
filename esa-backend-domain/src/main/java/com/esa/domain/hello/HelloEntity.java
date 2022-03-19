package com.esa.domain.hello;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloEntity {
    @Id
    private Long id;
    private String service;
    @Column(length = 1000)
    private String memo;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public HelloResponse makeResponse() {
        return HelloResponse.builder()
                .id(this.id)
                .memo(this.memo)
                .service(this.service)
                .createdAt(this.createdAt)
                .build();
    }
}
