package com.kilometer.backend.controller;

import com.kilometer.domain.homeModules.modules.keyVisual.KeyVisualService;
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

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class homeController {

    private final KeyVisualService keyVisualService;

    @GetMapping(BoUrlUtils.KEY_VISUAL)
    public String keyVisual(Model model) {
        List<KeyVisualResponse> keyVisualList = keyVisualService.findAllByKeyVisual();
        model.addAttribute("keyVisualList", keyVisualList);
        return "home/key_visual/keyVisual";
    }

    @GetMapping(BoUrlUtils.KEY_VISUAL_EDIT)
    public String getKeyVisual(Model model) {
        List<KeyVisualResponse> keyVisualList = keyVisualService.findAllByKeyVisual();
        model.addAttribute("keyVisualList", keyVisualList);
        return "home/key_visual/updateKeyVisual";
    }

    @PostMapping(BoUrlUtils.KEY_VISUAL_EDIT)
    public String updateKeyVisual(@ModelAttribute KeyVisualUpdateResponseList keyVisualUpdateResponseList,
                                  Principal principal) {
        keyVisualService.updateKeyVisual(keyVisualUpdateResponseList.getKeyVisualList(), principal.getName());
        return "redirect:/home/key_visual";
    }
}
