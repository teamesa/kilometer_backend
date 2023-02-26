package com.kilometer.domain.item.itemDetail;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.dto.DetailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class DetailCommandService {
    private final ItemDetailRepository repository;

    public void createItemDetail(DetailRequest request, ItemEntity mappedItem) {
        if (!isDetailItem(request)) {
            return;
        }

        ItemDetail detail = request.toItemDetailEntity();
        detail.setItemEntity(mappedItem);

        repository.save(detail);
    }

    private boolean isDetailItem(DetailRequest request) {
        if (StringUtils.hasText(request.getSource())) {
            return true;
        }

        if (StringUtils.hasText(request.getIntroduce())) {
            return true;
        }

        return false;
    }

    public void commandItemDetail(DetailRequest request, ItemEntity item) {
        DetailCommand command = getCommand(request, item);
        switch (command) {
            case UPDATE:
                updateItemDetail(request, item);
                break;
            case CREATE:
                createItemDetail(request, item);
                break;
            case DELETE:
                deleteItemDetail(item.getItemDetail());
                break;
        }
    }

    private DetailCommand getCommand(DetailRequest requestDetail, ItemEntity item) {
        ItemDetail savedDetail = item.getItemDetail();

        if (!isDetailItem(requestDetail)) {
            return DetailCommand.DELETE;
        }
        if (Objects.isNull(savedDetail)) {
            return DetailCommand.CREATE;
        }
        return DetailCommand.UPDATE;
    }

    private void updateItemDetail(DetailRequest request, ItemEntity item) {
        ItemDetail detail = item.getItemDetail();
        detail.update(request);
        repository.save(detail);
    }

    private void deleteItemDetail(ItemDetail itemDetail) {
        if (Objects.isNull(itemDetail)) {
            return;
        }
        repository.delete(itemDetail);
    }

}
