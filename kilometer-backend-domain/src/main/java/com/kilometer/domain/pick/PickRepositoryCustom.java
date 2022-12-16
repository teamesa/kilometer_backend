package com.kilometer.domain.pick;

import com.kilometer.domain.pick.dto.MostPickItemDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PickRepositoryCustom {

    List<MostPickItemDto> findMostPickTop4(LocalDateTime firstDate);
}
