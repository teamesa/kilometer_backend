package com.kilometer.backend.controller;

import com.kilometer.domain.archive.ArchiveService;
import com.kilometer.domain.archive.dto.ArchiveDeleteResponse;
import com.kilometer.domain.archive.dto.ArchiveDetailResponse;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.dto.ArchiveUpdateResponse;
import com.kilometer.domain.archive.dto.MyArchiveResponse;
import com.kilometer.domain.archive.like.dto.LikeResponse;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.dto.GeneralResponse;
import com.kilometer.domain.paging.RequestPagingStatus;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ArchiveController {

    private final static String ARCHIVE_TITLE = "아카이브";
    private final ArchiveService archiveService;

    @GetMapping(ApiUrlUtils.ITEM_ARCHIVES)
    @ApiOperation(value = "전시글에서 아카이브 조회")
    public GeneralResponse<ArchiveResponse> archives(
        @ApiParam(value = "조회할 전시글 ID", required = true) @PathVariable Long itemId,
        @ApiParam(value = "아카이브 페이지 정보", required = true) RequestPagingStatus requestPagingStatus,
        @ApiParam(value = "아카이브 정렬 기준", required = true, defaultValue = "MODIFY_DESC")
        @RequestParam(defaultValue = "MODIFY_DESC") ArchiveSortType sortType) {
        long userId = getLoginUserId();
        ArchiveResponse response = archiveService.findAllByItemIdAndUserId(itemId, userId,
            requestPagingStatus, sortType);
        return GeneralResponse.<ArchiveResponse>builder()
            .title(ARCHIVE_TITLE)
            .contents(response)
            .build();
    }

    @GetMapping(ApiUrlUtils.ARCHIVE_ID)
    @ApiOperation(value = "상세 아카이브 조회")
    public ArchiveDetailResponse archive(
        @ApiParam(value = "조회할 아카이브 ID", required = true) @PathVariable Long archiveId) {
        Long userId = getLoginUserId();
        return archiveService.findByArchiveIdAndUserId(archiveId, userId);
    }

    @PostMapping(ApiUrlUtils.ARCHIVE_ROOT)
    @ApiOperation(value = "신규 아카이브 등록")
    public ArchiveInfo saveArchive(
        @ApiParam(value = "등록할 아카이브 데이터", required = true) @RequestBody ArchiveRequest request) {
        long userId = getLoginUserId();
        return archiveService.save(userId, request);
    }

    @PutMapping(ApiUrlUtils.ARCHIVE_ID)
    @ApiOperation(value = "아카이브 수정")
    public ArchiveUpdateResponse updateArchive(
        @ApiParam(value = "수정할 아카이브 아이디", required = true) @PathVariable Long archiveId,
        @ApiParam(value = "수정할 아카이브 데이터", required = true) @RequestBody ArchiveRequest request) {
        long userId = getLoginUserId();
        return archiveService.update(userId, archiveId, request);
    }

    @DeleteMapping(ApiUrlUtils.ARCHIVE_ID)
    @ApiOperation(value = "아카이브 삭제")
    public ArchiveDeleteResponse deleteArchive(
        @ApiParam(value = "삭제할 아카이브 아이디", required = true) @PathVariable Long archiveId)
        throws IllegalAccessException {
        Long userId = getLoginUserId();
        return archiveService.delete(archiveId, userId);
    }


    @PostMapping(ApiUrlUtils.ARCHIVE_MY)
    @ApiOperation(value = "나의 아카이브 조회")
    public MyArchiveResponse myArchives(
        @ApiParam(value = "아카이브 페이지 정보", required = true) @RequestBody RequestPagingStatus requestPagingStatus) {
        Long userId = getLoginUserId();
        return archiveService.findAllByUserId(userId, requestPagingStatus,
            ArchiveSortType.MODIFY_DESC);
    }

    @PutMapping(ApiUrlUtils.ARCHIVE_LIKE)
    @ApiOperation(value = "아카이브 좋아요")
    public LikeResponse makeLikeResponse(@PathVariable Long archiveId,
        @RequestParam boolean status) {
        Long userId = getLoginUserId();
        return archiveService.like(archiveId, userId, status);
    }


}
