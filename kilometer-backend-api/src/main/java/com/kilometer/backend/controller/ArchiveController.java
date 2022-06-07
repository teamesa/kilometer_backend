package com.kilometer.backend.controller;

import com.kilometer.backend.security.security.CurrentUser;
import com.kilometer.backend.security.security.UserPrincipal;
import com.kilometer.domain.archive.ArchiveService;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.dto.ItemDetailResponse;
import com.kilometer.domain.paging.RequestPagingStatus;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrlUtils.ARCHIVE_ROOT)
public class ArchiveController {

    private final static String ARCHIVE_TITLE = "아카이브";
    private final static String MY_ARCHIVE_TITLE = "MY 아카이브";
    private final ArchiveService archiveService;

    @GetMapping(ApiUrlUtils.ARCHIVE_ITEM)
    public ItemDetailResponse<ArchiveResponse> archives(@PathVariable Long itemId, RequestPagingStatus requestPagingStatus, @RequestParam(defaultValue = "MODIFY_DESC") ArchiveSortType sortType) {
        ArchiveResponse response = archiveService.findAllByItemId(itemId,requestPagingStatus,sortType);
        return ItemDetailResponse.<ArchiveResponse>builder()
            .title(ARCHIVE_TITLE)
            .contents(response)
            .build();
    }

    @PostMapping
    public void saveArchive(@RequestBody ArchiveRequest request) {
        long userId = getLoginUserId();
        archiveService.save(userId,request);
    }

    @GetMapping(ApiUrlUtils.ARCHIVE_MY)
    @PreAuthorize("hasRole('USER')")
    public ItemDetailResponse<ArchiveResponse> myArchives(@CurrentUser UserPrincipal userPrincipal, RequestPagingStatus requestPagingStatus) {
        ArchiveResponse response = archiveService.findAllByUserId(userPrincipal.getId(), requestPagingStatus);
        return ItemDetailResponse.<ArchiveResponse>builder()
            .title(MY_ARCHIVE_TITLE)
            .contents(response)
            .build();
    }


}
