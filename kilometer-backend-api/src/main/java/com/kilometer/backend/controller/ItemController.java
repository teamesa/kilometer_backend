package com.kilometer.backend.controller;

import com.kilometer.backend.security.exception.ResourceNotFoundException;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.SummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/{itemId}/summary")
  public SummaryResponse getSummary(@PathVariable Long itemId) {
    return itemService.findToSummaryResponseById(itemId)
        .orElseThrow(() -> new ResourceNotFoundException("Item", "id", itemId));
  }

}
