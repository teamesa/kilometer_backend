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
import com.kilometer.domain.item.dto.ItemRequest;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.util.BoUrlUtils;
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
        List<ItemResponse> items = itemService.findItems();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping(BoUrlUtils.ITEM_ADD)
    public String addForm(Model model) {
        ItemResponse defaultOptions = makeDefaultOption();
        model.addAttribute("item", defaultOptions);
        return "form/addForm";
    }

    @PostMapping(BoUrlUtils.ITEM_ADD)
    public String addItem(@ModelAttribute ItemRequest item) {
        itemService.saveItem(item);
        return "redirect:/form/items";
    }

    @GetMapping(BoUrlUtils.ITEM_EDIT)
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        ItemResponse findOne = itemService.findOne(itemId);
        model.addAttribute("item", findOne);
        return "form/updateItemForm";
    }

    @PostMapping(BoUrlUtils.ITEM_EDIT)
    public String updateForm(@PathVariable Long itemId, @ModelAttribute ItemRequest item) {
        itemService.updateItem(itemId, item);
        return "redirect:/form/items";
    }

    @PostMapping(BoUrlUtils.ITEM_DELETE)
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
}
