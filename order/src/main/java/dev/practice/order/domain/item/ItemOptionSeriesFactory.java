package dev.practice.order.domain.item;

import dev.practice.order.domain.item.optiongroup.ItemOptionGroup;

import java.util.List;

/**
 * interface 로 하여 저장하는 로직(기술)을 바꿀수 있도록 한다.
 */
public interface ItemOptionSeriesFactory {
    List<ItemOptionGroup> store(ItemCommand.RegisterItemRequest request, Item item);
}
