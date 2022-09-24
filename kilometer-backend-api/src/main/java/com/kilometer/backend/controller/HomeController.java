package com.kilometer.backend.controller;

import com.kilometer.domain.homeModules.HomeApiResponse;
import com.kilometer.domain.homeModules.HomeModuleService;
import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrlUtils.HOME_ROOT)
public class HomeController {

    private final HomeModuleService moduleService;

    @GetMapping
    public HomeApiResponse homeApi() {
        return moduleService.home();
    }
}
