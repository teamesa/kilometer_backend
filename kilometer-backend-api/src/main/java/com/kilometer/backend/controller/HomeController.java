package com.kilometer.backend.controller;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

import com.kilometer.domain.homeModules.HomeApiResponse;
import com.kilometer.domain.homeModules.HomeRenderingService;
import com.kilometer.domain.homeModules.ModuleResponseDto;
import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrlUtils.HOME_ROOT)
public class HomeController {

    private final HomeRenderingService moduleService;

    @GetMapping(ApiUrlUtils.KEY_VISUAL)
    public ModuleResponseDto getKeyVisual() {
        Long userId = getLoginUserId();
        return moduleService.getKeyVisual(userId);
    }

    @GetMapping
    public HomeApiResponse homeApi() {
        Long userId = getLoginUserId();
        return moduleService.getHomeModules(userId);
    }
}
