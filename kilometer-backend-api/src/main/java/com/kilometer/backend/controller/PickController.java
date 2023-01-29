package com.kilometer.backend.controller;

import com.kilometer.domain.pick.PickService;
import com.kilometer.domain.pick.dto.MostPickResponse;
import com.kilometer.domain.pick.dto.MyPickResponse;
import com.kilometer.domain.pick.dto.PickRequest;
import com.kilometer.domain.pick.dto.PickResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

@RestController
@RequiredArgsConstructor
public class PickController {

    private final PickService pickService;

    @PutMapping(ApiUrlUtils.PICK_ITEM)
    public PickResponse makePickStatus(@PathVariable Long itemId, @RequestParam Boolean status) {
        Long userId = getLoginUserId();
        return pickService.makePickStatus(itemId, userId, status);
    }

    @PostMapping(ApiUrlUtils.PICK_ROOT)
    @ApiOperation(value = "Pick 정보 API")
    public MyPickResponse getMyPicks(@RequestBody PickRequest pickRequest) {
        return pickService.getMyPicks(pickRequest, getLoginUserId());
    }

    @GetMapping(ApiUrlUtils.PICK_MOST)
    @ApiOperation(value = "많이 PICK한 문화생활 정보 API")
    public MostPickResponse getMostPicks() {
        return pickService.getMostPicks();
    }
}