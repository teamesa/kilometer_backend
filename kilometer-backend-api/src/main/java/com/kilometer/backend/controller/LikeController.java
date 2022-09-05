package com.kilometer.backend.controller;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

import com.kilometer.domain.archive.like.LikeService;
import com.kilometer.domain.archive.like.dto.LikeResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrlUtils.LIKE_ROOT)
public class LikeController {

    private final LikeService likeService;

    @PutMapping(ApiUrlUtils.LIKE_ARCHIVE)
    public LikeResponse makeLikeResponse(@PathVariable Long archiveId, @RequestParam boolean status) {
        Long userId = getLoginUserId();
        return likeService.makeLikeStatus(archiveId, userId, status);
    }
}
