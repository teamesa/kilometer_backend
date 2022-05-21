package com.kilometer.backend.controller;

import com.kilometer.backend.security.security.CurrentUser;
import com.kilometer.backend.security.security.UserPrincipal;
import com.kilometer.domain.archive.ArchiveService;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.dto.ItemDetailResponse;
import com.kilometer.domain.paging.RequestPagingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/archive")
public class ArchiveController {

    private final static String ARCHIVE_TITLE = "아카이브";
    private final static String MY_ARCHIVE_TITLE = "MY 아카이브";
    private final ArchiveService archiveService;

    @GetMapping("/{itemId}")
    public ItemDetailResponse<ArchiveResponse> archives(@PathVariable Long itemId, RequestPagingStatus requestPagingStatus) {
        ArchiveResponse response = archiveService.findAllByItemId(itemId,requestPagingStatus);
        return ItemDetailResponse.<ArchiveResponse>builder()
            .title(ARCHIVE_TITLE)
            .contents(response)
            .build();
    }

    @PostMapping
    public void saveArchive(@CurrentUser UserPrincipal userPrincipal, @RequestBody ArchiveRequest request) {
        archiveService.save(userPrincipal.getId(),request);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ItemDetailResponse<ArchiveResponse> myArchives(@CurrentUser UserPrincipal userPrincipal, RequestPagingStatus requestPagingStatus) {
        ArchiveResponse response = archiveService.findAllByUserId(userPrincipal.getId(), requestPagingStatus);
        return ItemDetailResponse.<ArchiveResponse>builder()
            .title(MY_ARCHIVE_TITLE)
            .contents(response)
            .build();
    }


}
