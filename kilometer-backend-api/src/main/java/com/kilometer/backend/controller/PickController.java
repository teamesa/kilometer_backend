package com.kilometer.backend.controller;

import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrlUtils.PICK_ROOT)
public class PickController {

    @PutMapping(ApiUrlUtils.PICK_ITEM)
    public boolean makePickStatus(@PathVariable Long itemId, @RequestParam Boolean status) {
        return false;
    }
}
