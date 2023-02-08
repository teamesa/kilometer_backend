package com.kilometer.backend.controller;

import com.kilometer.backend.util.ApiGenerator;
import com.kilometer.domain.homeModules.HomeApiResponse;
import com.kilometer.domain.homeModules.HomeRenderingService;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeRenderingService moduleService;
    private final ApiGenerator apiGenerator;

    @GetMapping(ApiUrlUtils.KEY_VISUAL)
    @ApiOperation(value = "홈 모듈 중 키비주얼만 조회")
    public ModuleResponseDto getKeyVisual() {
        Long userId = getLoginUserId();
        return moduleService.getKeyVisual(userId);
    }

    @GetMapping(ApiUrlUtils.HOME_ROOT)
    @ApiOperation(value = "홈 모듈 모두 조회")
    public HomeApiResponse homeApi() {
        Long userId = getLoginUserId();
        return apiGenerator.generatorGetHomeMoulesApi(moduleService.getHomeModules(userId));
    }
}
