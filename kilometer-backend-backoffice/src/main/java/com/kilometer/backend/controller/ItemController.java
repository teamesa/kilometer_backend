package com.kilometer.backend.controller;

import static com.kilometer.domain.item.ExhibitionType.EXHIBITION;
import static com.kilometer.domain.item.ExposureType.ON;
import static com.kilometer.domain.item.FeeType.FREE;
import static com.kilometer.domain.item.RegionType.SEOUL;

import com.kilometer.domain.item.ExhibitionType;
import com.kilometer.domain.item.ExposureType;
import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.RegionType;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.item.dto.ItemSaveRequest;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/form/items")
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
        List<ItemResponse> items = itemService.findItems();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        ItemResponse defaultOptions = makeDefaultOption();
        model.addAttribute("item", defaultOptions);
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute ItemSaveRequest item) {
        itemService.saveItem(item);
        return "redirect:/form/items";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        ItemResponse findOne = itemService.findOne(itemId);
        model.addAttribute("item", findOne);
        return "form/updateItemForm";
    }

//    @PostMapping("/{itemId}/edit")
//    public String updateForm(@PathVariable Long itemId, @ModelAttribute ItemForm item) throws IOException {
//        itemService.updateItem(itemId, item.makeItemUpdateRequest(
//                fileUpdateCheck(itemId, fileUtils.getS3ImageUrl(item)),
//                fileUtils.getS3MultiImageUrl(item),
//                fileUtils.getDeleteImage(item)
//        ));
//        return "redirect:/form/items";
//    }

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

    private ItemResponse makeDefaultOption() {
        return ItemResponse.builder()
            .exhibitionType(EXHIBITION)
            .regionType(SEOUL)
            .exposureType(ON)
            .feeType(FREE)
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
            .build();
    }

    private String fileUpdateCheck(Long itemId, String s3ImageUrl) {
        if (s3ImageUrl.equals("")) {
            ItemResponse findItem = itemService.findOne(itemId);
            s3ImageUrl = findItem.getThumbnailImageUrl();
        }
        return s3ImageUrl;
    }
}
