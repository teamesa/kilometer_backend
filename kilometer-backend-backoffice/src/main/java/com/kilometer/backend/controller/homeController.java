package com.kilometer.backend.controller;

import com.kilometer.domain.homeModules.HomeService;
import com.kilometer.domain.homeModules.dto.ModuleDeleteResponseList;
import com.kilometer.domain.homeModules.dto.ModuleResponse;
import com.kilometer.domain.homeModules.dto.ModuleResponseList;
import com.kilometer.domain.homeModules.dto.ModuleTypeDto;
import com.kilometer.domain.homeModules.dto.ModuleUpdateRequestList;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.homeModules.modules.keyVisual.dto.KeyVisualUpdateResponseList;
import com.kilometer.domain.util.BoUrlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class homeController {

    private final HomeService homeService;

    @GetMapping(BoUrlUtils.KEY_VISUAL)
    public String keyVisual(Model model) {
        List<KeyVisualResponse> keyVisualList = homeService.findAllByKeyVisual();
        model.addAttribute("keyVisualList", keyVisualList);
        return "home/key_visual/keyVisual";
    }

    @GetMapping(BoUrlUtils.KEY_VISUAL_EDIT)
    public String getUpdateKeyVisual() {
        return "home/key_visual/updateKeyVisual";
    }

    @ResponseBody
    @GetMapping(BoUrlUtils.KEY_VISUAL_LIST)
    public List<KeyVisualResponse> getKeyVisual() {
        return homeService.findAllByKeyVisual();
    }

    @ResponseBody
    @PostMapping(BoUrlUtils.KEY_VISUAL_EDIT)
    public List<String> updateKeyVisual(@RequestBody KeyVisualUpdateResponseList keyVisualUpdateResponseList,
                                  Principal principal) {
        return homeService.updateAfterValidateKeyVisual(
                keyVisualUpdateResponseList.getKeyVisualList(), principal.getName());
//        return "redirect:/home/key_visual";
    }

    @GetMapping(BoUrlUtils.HOME_MODULES)
    public String modules(Model model) {
        List<ModuleResponse> moduleList = homeService.findAllByModule();
        model.addAttribute("moduleList", moduleList);
        return "home/modules";
    }

    @ResponseBody
    @GetMapping(BoUrlUtils.HOME_MODULES_LIST)
    public List<ModuleResponse> getModules() {
        return homeService.findAllByModule();
    }

    @ResponseBody
    @GetMapping(BoUrlUtils.HOME_MODULES_TYPE)
    public List<ModuleTypeDto> getModulesType() {
        return homeService.makeModuleType();
    }

    @GetMapping(BoUrlUtils.HOME_MODULES_EDIT)
    public String getModules(Model model) {
        ModuleResponseList moduleList = homeService.findAllByUpdateModule();
        model.addAttribute("moduleList", moduleList);
        return "home/updateModules";
    }

    @ResponseBody
    @PostMapping(BoUrlUtils.HOME_MODULES_EDIT)
    public List<String> updateModules(
            @RequestBody ModuleUpdateRequestList moduleUpdateRequestList, Principal principal) {
        return homeService.updateModule(moduleUpdateRequestList.getModuleList(), principal.getName());
    }

    @ResponseBody
    @PostMapping(BoUrlUtils.HOME_MODULES_DELETE)
    public void deleteModule(@RequestBody ModuleDeleteResponseList moduleDeleteResponseList) {
        homeService.deleteModule(moduleDeleteResponseList.getDeleteModuleList());
    }
}
