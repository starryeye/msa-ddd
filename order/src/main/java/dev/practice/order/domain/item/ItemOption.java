package dev.practice.order.domain.item;

import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_options")
public class ItemOption extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_option_group_id")
    private ItemOptionGroup itemOptionGroup;

    private Integer ordering;
    private String itemOptionName;
    private Long itemOptionPrice;

    //ItemOption 이 생성되기 위해선 ItemOptionGroup 이 무조건 필요하다.
    @Builder
    public ItemOption(
            ItemOptionGroup itemOptionGroup,
            Integer ordering,
            String itemOptionName,
            Long itemOptionPrice
    ) {
        if (itemOptionGroup == null) throw new InvalidParamException("ItemOption.itemOptionGroup");
        if (ordering == null) throw new InvalidParamException("ItemOption.ordering");
        if (StringUtils.isBlank(itemOptionName)) throw new InvalidParamException("ItemOption.itemOptionName");
        if (itemOptionPrice == null) throw new InvalidParamException("ItemOption.itemOptionPrice");

        this.itemOptionGroup = itemOptionGroup;
        this.ordering = ordering;
        this.itemOptionName = itemOptionName;
        this.itemOptionPrice = itemOptionPrice;
    }
}
