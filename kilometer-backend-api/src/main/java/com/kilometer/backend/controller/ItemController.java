package com.kilometer.backend.controller;

import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.DetailResponse;
import com.kilometer.domain.item.dto.ItemInfoResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(ApiUrlUtils.ITEM_ID)
    @ApiOperation(value = "전시글 기본정보 API")
    public ItemInfoResponse getInfo(
        @ApiParam(value = "조회할 전시글 ID", required = true) @PathVariable Long itemId) {
        long userId = getLoginUserId();
        return itemService.getItem(itemId, userId);
    }

    /**
     * 전시글의 소개글과 사진을 반환하는 API
     * @deprecated 전시글 조회 api("api/items/{itemId}")로 이전
     */
    @Deprecated
    @GetMapping("/api/items/{itemId}/detail")
    @ApiOperation(value = "전시글 소개 API")
    public DetailResponse getDetail(
        @ApiParam(value = "조회할 전시글 ID", required = true) @PathVariable Long itemId) {
        return itemService.getItemDetail(itemId);
    }

    @GetMapping(ApiUrlUtils.ITEM_SUMMARY)
    @ApiOperation(value ="전시글 제목, 리스트이미지, 아카이브 작성 여부만 조회")
    public void getSummary(@PathVariable Long itemId) {

    }

}
