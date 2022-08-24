package com.kilometer.backend.controller;

import com.kilometer.backend.security.security.CurrentUser;
import com.kilometer.backend.security.security.UserPrincipal;
import com.kilometer.domain.pick.PickService;
import com.kilometer.domain.pick.dto.MyPickResponse;
import com.kilometer.domain.pick.dto.PickRequest;
import com.kilometer.domain.pick.dto.PickResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrlUtils.PICK_ROOT)
public class PickController {
    private final PickService pickService;

    @PreAuthorize("hasRole('USER')")
    @PutMapping(ApiUrlUtils.PICK_ITEM)
    public PickResponse makePickStatus(@PathVariable Long itemId, @RequestParam Boolean status, @CurrentUser UserPrincipal userPrincipal) {
        return pickService.makePickStatus(itemId, userPrincipal.getId(), status);
    }

    @PostMapping
    @ApiOperation(value = "Pick 정보 API")
    public MyPickResponse getMyPick(@RequestBody PickRequest pickRequest) {
        return pickService.getMyPick(pickRequest, getLoginUserId());
    }
}