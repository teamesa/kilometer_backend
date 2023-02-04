package com.kilometer.backend.controller.dto.module.realtimearchive;

import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {

    private String name;
    private String photoUrl;

    static UserDto from(RealTimeArchiveDto realTimeArchiveDto) {
        return UserDto.builder()
                .name(realTimeArchiveDto.getUserName())
                .photoUrl(realTimeArchiveDto.getUserImageUrl())
                .build();
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
