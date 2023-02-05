package com.kilometer.domain.pick;

import com.kilometer.domain.pick.dto.MostPickItemDto;
import com.kilometer.domain.pick.dto.PickItemDto;
import com.kilometer.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PickRepositoryCustom {

    Page<PickItemDto> findAllMyPickByPickedUser(Pageable pageable, User pickedUser);

    List<MostPickItemDto> findMostPickTop4(LocalDateTime firstDate);
}
