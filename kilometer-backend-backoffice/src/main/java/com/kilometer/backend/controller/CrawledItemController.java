package com.kilometer.backend.controller;

import com.kilometer.domain.crawledItem.CrawledItemService;
import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import com.kilometer.domain.crawledItem.dto.CrawledItemPageResponse;
import com.kilometer.domain.crawledItem.dto.CrawledItemResponse;
import com.kilometer.domain.util.BoUrlUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CrawledItemController {

    private final CrawledItemService crawledItemService;

    @GetMapping(BoUrlUtils.CRAWLED_ITEM)
    public String getCrawledItem(@RequestParam(value = "page", defaultValue = "0") int page,
                                 Model model) {
        CrawledItemPageResponse crawledItemPage = crawledItemService.getCralwedItem(page);
        model.addAttribute("crawledItemPage", crawledItemPage);
        return "form/crawledItem";
    }
}
