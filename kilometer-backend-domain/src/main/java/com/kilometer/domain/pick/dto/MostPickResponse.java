package com.kilometer.domain.pick.dto;

import com.kilometer.domain.converter.listItem.dto.ListItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MostPickResponse {

    @Builder.Default
    private List<ListItem> contents = List.of();
}
