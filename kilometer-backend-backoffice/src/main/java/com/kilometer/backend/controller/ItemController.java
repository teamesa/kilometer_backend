package com.kilometer.backend.controller;

import com.kilometer.backend.controller.dto.ItemForm;
import com.kilometer.backend.controller.file.S3Uploader;
import com.kilometer.domain.item.*;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemSaveRequest;
import com.kilometer.domain.item.dto.ItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.kilometer.domain.item.ExhibitionType.EXHIBITION;
import static com.kilometer.domain.item.FeeType.FREE;
import static com.kilometer.domain.item.ExposureType.ON;
import static com.kilometer.domain.item.RegionType.SEOUL;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final S3Uploader s3Uploader;

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
        List<ItemResponse> items = itemService.findItems();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        ItemResponse defaultOptions = ItemResponse.builder().exhibitionType(EXHIBITION).regionType(SEOUL).exposureType(ON).fee(FREE).startDate(LocalDate.now()).endDate(LocalDate.now()).build();
        model.addAttribute("item", defaultOptions);
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute ItemForm item) throws IOException {
        String s3ImageUrl = fileExists(item.getImage());
        ArrayList<String> multiS3ImageUrl = new ArrayList<>();
        for (int i = 0; i < item.getDetailImageUrl().size(); i++) {
            multiS3ImageUrl.add(fileExists(item.getDetailImageUrl().get(i)));
        }
        ItemSaveRequest build = ItemSaveRequest.builder()
                .exhibitionType(item.getExhibitionType())
                .exposureType(item.getExposureType())
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
                .introduce(item.getIntroduce())
                .detailImageUrl(multiS3ImageUrl)
                .build();
        itemService.saveItem(build);
        return "redirect:/form/items";
    }

    private String fileExists(MultipartFile image) throws IOException {
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
        String imageUrl = updateFileExists(itemId, item);
        ItemUpdateRequest build = ItemUpdateRequest.builder()
                .exhibitionType(item.getExhibitionType())
                .exposureType(item.getExposureType())
                .image(imageUrl)
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
                .introduce(item.getIntroduce())
                .build();
        itemService.updateItem(itemId, build);
        return "redirect:/form/items";
    }

    private String updateFileExists(Long itemId, ItemForm item) throws IOException {
        MultipartFile image = item.getImage();
        String imageUrl;
        String originalFilename = image.getOriginalFilename();
        if (StringUtils.hasText(originalFilename)) {
            imageUrl = s3Uploader.upload(image, "static");
        } else {
            ItemResponse findItem = itemService.findOne(itemId);
            imageUrl = findItem.getImage();
        }
        return imageUrl;
    }

    @PostMapping("/{itemId}/delete")
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/form/items";
    }

    @GetMapping("/response-test")
    @ResponseBody
    public List<ItemResponse> responseItemEntity() {
        return itemService.findItems();
    }

}
