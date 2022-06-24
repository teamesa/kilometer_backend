package com.kilometer.backend.controller;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.ExposureType;
import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.RegionType;
import com.kilometer.domain.item.dto.ItemRequest;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.util.BoUrlUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(BoUrlUtils.ITEM_ROOT)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @ModelAttribute("exhibitionTypes")
    public ExhibitionType[] exhibitionTypes() {
        return ExhibitionType.values();
    }

    @ModelAttribute("exposureTypes")
    public ExposureType[] exposureTypes() {
        return ExposureType.values();
    }

    @ModelAttribute("feeTypes")
    public FeeType[] feeTypes() {
        return FeeType.values();
    }

    @ModelAttribute("regionTypes")
    public RegionType[] regionTypes() {
        return RegionType.values();
    }

    @GetMapping
    public String items(Model model) {
        List<ItemResponse> items = itemService.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping(BoUrlUtils.ITEM_ADD)
    public String getEmptyItem(Model model) {
        ItemResponse itemResponse = ItemResponse.empty();
        model.addAttribute("item", itemResponse);
        return "form/addForm";
    }

    @PostMapping(BoUrlUtils.ITEM_ADD)
    public String addItem(@ModelAttribute ItemRequest itemRequest) {
        itemService.saveItem(itemRequest);
        return "redirect:/form/items";
    }

    @GetMapping(BoUrlUtils.ITEM_EDIT)
    public String getItem(@PathVariable("itemId") Long itemId, Model model) {
        ItemResponse itemResponse = itemService.findOne(itemId);
        model.addAttribute("item", itemResponse);
        return "form/updateItemForm";
    }

    @PostMapping(BoUrlUtils.ITEM_EDIT)
    public String updateItem(@PathVariable Long itemId, @ModelAttribute ItemRequest itemRequest) {
        itemService.updateItem(itemId, itemRequest);
        return "redirect:/form/items";
    }

    @PostMapping(BoUrlUtils.ITEM_DELETE)
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/form/items";
    }
}
