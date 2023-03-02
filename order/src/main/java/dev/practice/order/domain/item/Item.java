package dev.practice.order.domain.item;

import com.google.common.collect.Lists;
import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.common.util.TokenGenerator;
import dev.practice.order.domain.AbstractEntity;
import dev.practice.order.domain.item.optiongroup.ItemOptionGroup;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "items")
public class Item extends AbstractEntity {

    private static final String PREFIX_ITEM = "itm_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemToken; //Root Entity 이므로 Token 필요

    private Long partnerId;
    private String itemName;
    private Long itemPrice;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ItemOptionGroup> itemOptionGroupList = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PREPARE("판매준비중"),
        ON_SALES("판매중"),
        END_OF_SALES("판매종료");

        private final String description;
    }

    @Builder
    public Item(Long partnerId, String itemName, Long itemPrice) {

        if(partnerId == null) throw new InvalidParamException("Item.partnerId");
        if(StringUtils.isBlank(itemName)) throw new InvalidParamException("Item.itemName");
        if(itemPrice == null) throw new InvalidParamException("Item.itemPrice");

        this.itemToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_ITEM);
        this.partnerId = partnerId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.status = Status.PREPARE;
    }

    public void changePrepare() {
        this.status = Status.PREPARE;
    }

    public void changeOnSales() {
//        if(this.status == Status.END_OF_SALES) throw new InvalidParamException("판매종료된 상품은 판매중으로 변경 불가능.");
        this.status = Status.ON_SALES;
    }

    public void changeEndOfSales() {
        this.status = Status.END_OF_SALES;
    }
}
