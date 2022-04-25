package com.kilometer.backend.controller;

import com.kilometer.backend.controller.dto.ItemForm;
import com.kilometer.backend.controller.file.S3Uploader;
import com.kilometer.domain.item.*;
import com.kilometer.domain.item.dto.ItemListResponse;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemSaveRequest;
import com.kilometer.domain.item.dto.ItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final S3Uploader s3Uploader;

    @ModelAttribute("progressTypes")
    public ProgressType[] progressTypes() {
        return ProgressType.values();
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
        List<ItemListResponse> items = itemService.findItems();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemEntity());
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute ItemForm item) throws IOException {
        String s3ImageUrl = s3Uploader.upload(item.getImage(), "static");
        ItemSaveRequest build = ItemSaveRequest.builder()
                .progressType(item.getProgressType())
                .image(s3ImageUrl)
                .title(item.getTitle())
                .term(item.getTerm())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .regionType(item.getRegionType())
                .place(item.getPlace())
                .fee(item.getFee())
                .price(item.getPrice())
                .url(item.getUrl())
                .build();
        itemService.saveItem(build);
        return "redirect:/form/items";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        ItemResponse findOne = itemService.findOne(itemId);
        model.addAttribute("item", findOne);
        return "form/updateItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateForm(@PathVariable Long itemId, @ModelAttribute ItemForm item) throws IOException {
        String s3ImageUrl = s3Uploader.upload(item.getImage(), "static");
        ItemUpdateRequest build = ItemUpdateRequest.builder()
                .progressType(item.getProgressType())
                .image(s3ImageUrl)
                .title(item.getTitle())
                .term(item.getTerm())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .regionType(item.getRegionType())
                .place(item.getPlace())
                .fee(item.getFee())
                .price(item.getPrice())
                .url(item.getUrl())
                .build();
        itemService.updateItem(itemId, build);
        return "redirect:/form/items";
    }

    @PostMapping("/{itemId}/delete")
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/form/items";
    }
}
