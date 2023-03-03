package dev.practice.order.interfaces.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * TODO: validation Annotation 적용
 */
public class ItemDto {

    @Getter
    @Setter
    @ToString
    public static class ItemRequestDtoForRegister {
        private String partnerToken;
        private String itemName;
        private Long itemPrice;
        private List<ItemOptionGroupRequestDtoForRegister> itemOptionGroupRequestDtoForRegisterList;
    }

    @Getter
    @Setter
    @ToString
    public static class ItemOptionGroupRequestDtoForRegister {
        private Integer ordering;
        private String itemOptionGroupName;
        private List<ItemOptionRequestDtoForRegister> itemOptionRequestDtoForRegisterList;
    }

    @Getter
    @Setter
    @ToString
    public static class ItemOptionRequestDtoForRegister {
        private Integer ordering;
        private String itemOptionName;
        private Long itemOptionPrice;
    }

    @Getter
    @Setter
    @ToString
    public static class ItemTokenResponseDtoForRegister {
        private String itemToken;
    }
}
