package com.kilometer.backend.controller;

import com.kilometer.domain.archive.ArchiveService;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.dto.ItemDetailResponse;
import com.kilometer.domain.paging.RequestPagingStatus;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
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
    @ApiOperation(value = "전시글에서 아카이브 조회")
    public ItemDetailResponse<ArchiveResponse> archives(
        @ApiParam(value = "조회할 전시글 ID", required = true) @PathVariable Long itemId,
        @ApiParam(value = "아카이브 페이지 정보", required = true) RequestPagingStatus requestPagingStatus,
        @ApiParam(value = "아카이브 정렬 기준", required = true, defaultValue = "MODIFY_DESC")
        @RequestParam(defaultValue = "MODIFY_DESC") ArchiveSortType sortType) {
        ArchiveResponse response = archiveService.findAllByItemId(itemId, requestPagingStatus,
            sortType);
        return ItemDetailResponse.<ArchiveResponse>builder()
            .title(ARCHIVE_TITLE)
            .contents(response)
            .build();
    }

    @PostMapping
    public ArchiveInfo saveArchive(
        @ApiParam(value = "등록할 아카이브 데이터", required = true) @RequestBody ArchiveRequest request) {
        long userId = getLoginUserId();
        return archiveService.save(userId, request);
    }

    @PutMapping
    public ArchiveInfo updateArchive(
        @ApiParam(value = "수정할 아카이브 데이터", required = true) @RequestBody ArchiveRequest request) {
        long userId = getLoginUserId();
        return archiveService.update(userId, request);
    }


    @GetMapping(ApiUrlUtils.ARCHIVE_MY)
    public ItemDetailResponse<ArchiveResponse> myArchives(
        @ApiParam(value = "아카이브 페이지 정보", required = true) RequestPagingStatus requestPagingStatus) {
        Long userId = getLoginUserId();
        ArchiveResponse response = archiveService.findAllByUserId(userId, requestPagingStatus);
        return ItemDetailResponse.<ArchiveResponse>builder()
            .title(MY_ARCHIVE_TITLE)
            .contents(response)
            .build();
    }


}
