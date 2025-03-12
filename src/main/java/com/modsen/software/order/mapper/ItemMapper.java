package com.modsen.software.order.mapper;

import com.modsen.software.order.dto.request.ItemRequest;
import com.modsen.software.order.dto.response.ItemResponse;
import com.modsen.software.order.model.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    List<ItemResponse> toListOfItemResponse(List<Item> items);
    ItemResponse toItemResponse(Item item);
    List<Item> toListOfItems(List<ItemRequest> itemRequests);
    Item toItemModel(ItemRequest itemRequest);
}
