package com.kilometer.backend.controller;

import com.kilometer.backend.security.security.CurrentUser;
import com.kilometer.backend.security.security.UserPrincipal;
import com.kilometer.domain.pick.PickResponse;
import com.kilometer.domain.pick.PickService;
import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
