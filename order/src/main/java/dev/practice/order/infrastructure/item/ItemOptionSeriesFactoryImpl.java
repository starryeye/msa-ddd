package dev.practice.order.infrastructure.item;

import dev.practice.order.domain.item.Item;
import dev.practice.order.domain.item.ItemCommand;
import dev.practice.order.domain.item.ItemOptionSeriesFactory;
import dev.practice.order.domain.item.option.ItemOption;
import dev.practice.order.domain.item.option.ItemOptionStore;
import dev.practice.order.domain.item.optiongroup.ItemOptionGroup;
import dev.practice.order.domain.item.optiongroup.ItemOptionGroupStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemOptionSeriesFactoryImpl implements ItemOptionSeriesFactory {

    private final ItemOptionGroupStore itemOptionGroupStore;
    private final ItemOptionStore itemOptionStore;

    /**
     * TODO: cascade 사용
     */
    @Override
    public List<ItemOptionGroup> store(ItemCommand.RegisterItemRequest command, Item item) {

        var itemOptionGroupRequestList = command.getItemOptionGroupRequestList();

        //itemOptionGroup 이 없으면 그대로 건너뛴다. (Item 만 저장)
        if(CollectionUtils.isEmpty(itemOptionGroupRequestList))
            return Collections.emptyList();

        return itemOptionGroupRequestList.stream()
                .map(requestItemOptionGroup -> {
                    // itemOptionGroup store
                    var initItemOptionGroup = requestItemOptionGroup.toEntity(item);
                    var itemOptionGroup = itemOptionGroupStore.store(initItemOptionGroup);

                    // itemOption store
                    requestItemOptionGroup.getItemOptionRequestList().forEach(requestItemOption -> {
                        var initItemOption = requestItemOption.toEntity(itemOptionGroup);
                        itemOptionStore.store(initItemOption);
                    });

                    return itemOptionGroup;
                }).collect(Collectors.toList());
    }
}
