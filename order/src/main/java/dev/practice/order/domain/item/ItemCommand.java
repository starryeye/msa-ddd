package dev.practice.order.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ItemCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterItemRequest {
        private final String itemName;
        private final Long itemPrice;
        private final List<RegisterItemOptionGroupRequest> itemOptionGroupRequestList; //ex) 색상, 사이즈

        public Item toEntity(Long partnerId) {
            return Item.builder()
                    .partnerId(partnerId)
                    .itemName(itemName)
                    .itemPrice(itemPrice)
                    .build();
        }
    }
}
