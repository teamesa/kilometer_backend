package com.kilometer.backend.controller;

import com.kilometer.backend.controller.dto.ItemForm;
import com.kilometer.backend.controller.file.S3Uploader;
import com.kilometer.domain.item.*;
import com.kilometer.domain.item.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.kilometer.domain.item.ExhibitionType.EXHIBITION;
import static com.kilometer.domain.item.FeeType.FREE;
import static com.kilometer.domain.item.ProgressType.ON;
import static com.kilometer.domain.item.RegionType.SEOUL;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final S3Uploader s3Uploader;

    @ModelAttribute("exhibitionType")
    public ExhibitionType[] exhibitionTypes() {
        return ExhibitionType.values();
    }

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
        model.addAttribute("item", new ItemResponse(EXHIBITION, ON, SEOUL, FREE));
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute ItemForm item) throws IOException {
        String s3ImageUrl = fileExists(item);
        ItemSaveRequest build = ItemSaveRequest.builder()
                .exhibitionType(item.getExhibitionType())
                .progressType(item.getProgressType())
                .image(s3ImageUrl)
                .title(item.getTitle())
                .startDate(item.getStartDate())
                .endDate(item.getEndDate())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .regionType(item.getRegionType())
                .place(item.getPlace())
                .fee(item.getFee())
                .price(item.getPrice())
                .url(item.getUrl())
                .time(item.getTime())
                .ticketUrl(item.getTicketUrl())
                .build();
        itemService.saveItem(build);
        return "redirect:/form/items";
    }

    private String fileExists(ItemForm item) throws IOException {
        MultipartFile image = item.getImage();
        String s3ImageUrl = "";
        String originalFilename = image.getOriginalFilename();
        if (StringUtils.hasText(originalFilename)) {
            s3ImageUrl = s3Uploader.upload(image, "static");
        }
        return s3ImageUrl;
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        ItemResponse findOne = itemService.findOne(itemId);
        model.addAttribute("item", findOne);
        return "form/updateItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateForm(@PathVariable Long itemId, @ModelAttribute ItemForm item) throws IOException {
        String s3ImageUrl = fileExists(item);
        ItemUpdateRequest build = ItemUpdateRequest.builder()
                .exhibitionType(item.getExhibitionType())
                .progressType(item.getProgressType())
                .image(s3ImageUrl)
                .title(item.getTitle())
                .startDate(item.getStartDate())
                .endDate(item.getEndDate())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .regionType(item.getRegionType())
                .place(item.getPlace())
                .fee(item.getFee())
                .price(item.getPrice())
                .url(item.getUrl())
                .time(item.getTime())
                .ticketUrl(item.getTicketUrl())
                .build();
        itemService.updateItem(itemId, build);
        return "redirect:/form/items";
    }

    @PostMapping("/{itemId}/delete")
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/form/items";
    }

    @GetMapping("/response-test")
    @ResponseBody
    public List<ItemAndDetailListResponse> responseItemEntity() {
        List<ItemAndDetailListResponse> itemsAndDetail = itemService.findItemsAndDetail();
        return itemsAndDetail;
    }

}
