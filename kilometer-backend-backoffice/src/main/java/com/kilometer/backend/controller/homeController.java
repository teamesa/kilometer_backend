package com.kilometer.backend.controller;

import com.kilometer.domain.home.HomeService;
import com.kilometer.domain.home.keyVisual.dto.KeyVisualResponse;
import com.kilometer.domain.home.keyVisual.dto.KeyVisualUpdateResponseList;
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

    private final HomeService homeService;

    @GetMapping(BoUrlUtils.KEY_VISUAL)
    public String keyVisual(Model model) {
        List<KeyVisualResponse> keyVisualList = homeService.findAll();
        model.addAttribute("keyVisualList", keyVisualList);
        return "home/key_visual/keyVisual";
    }

    @GetMapping(BoUrlUtils.KEY_VISUAL_EDIT)
    public String getKeyVisual(Model model) {
        List<KeyVisualResponse> keyVisualList = homeService.findAll();
        model.addAttribute("keyVisualList", keyVisualList);
        return "home/key_visual/updateKeyVisual";
    }

    @PostMapping(BoUrlUtils.KEY_VISUAL_EDIT)
    public String updateKeyVisual(@ModelAttribute KeyVisualUpdateResponseList keyVisualUpdateResponseList,
                                  Principal principal) {
        homeService.updateKeyVisual(keyVisualUpdateResponseList.getKeyVisualList(), principal.getName());
        return "redirect:/home/key_visual";
    }

    @GetMapping(BoUrlUtils.IMAGE_SWIPE)
    public String imageSwipe(Model model) {
        return "home/image_swipe/imageSwipe";
    }

    @GetMapping(BoUrlUtils.IMAGE_SWIPE_EDIT)
    public String getImageSwipe(Model model) {
        return "home/image_swipe/updateImageSwipe";
    }

    @PostMapping(BoUrlUtils.IMAGE_SWIPE_EDIT)
    public String updateImageSwipe(Model model) {
        return "redirect:/home/image_swipe";
    }
}
