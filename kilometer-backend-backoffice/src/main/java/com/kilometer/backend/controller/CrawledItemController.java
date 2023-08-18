package com.kilometer.backend.controller;

import com.kilometer.domain.crawledItem.CrawledItemService;
import com.kilometer.domain.crawledItem.dto.CrawledItemPageResponse;
import com.kilometer.domain.util.BoUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CrawledItemController {

    private final CrawledItemService crawledItemService;

    @GetMapping(BoUrlUtils.CRAWLED_ITEM)
    public String getCrawledItem(@RequestParam(value = "page", defaultValue = "0") int page,
                                 Model model) {
        CrawledItemPageResponse crawledItemPage = crawledItemService.getCrawledItem(page);
        model.addAttribute("crawledItemPage", crawledItemPage);
        return "form/crawledItem";
    }

    @DeleteMapping(BoUrlUtils.CRAWLED_ITEM_DELETEION + "/{crawledItemId}")
    public ResponseEntity<Void> deleteCrawledItem(@PathVariable("crawledItemId") Long crawledItemId) {
        crawledItemService.deleteCrawledItem(crawledItemId);
        return ResponseEntity.ok().build();
    }
}
