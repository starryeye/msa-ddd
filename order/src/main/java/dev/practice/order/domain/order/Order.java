package dev.practice.order.domain.order;

import com.google.common.collect.Lists;
import dev.practice.order.common.exception.IllegalStatusException;
import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.common.util.TokenGenerator;
import dev.practice.order.domain.AbstractEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends AbstractEntity {

    private static final String ORDER_PREFIX ="ord_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderToken;
    private Long userId;
    private String payMethod;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItemList = Lists.newArrayList();

    private String receiverName;
    private String receiverPhone;
    private String receiverZipcode;
    private String receiverAddress1;
    private String receiverAddress2;
    private String etcMessage;

    private ZonedDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        INIT("주문시작"),
        ORDER_COMPLETE("주문완료"),
        DELIVERY_PREPARE("배송준비"),
        IN_DELIVERY("배송중"),
        DELIVERY_COMPLETE("배송완료");

        private final String description;
    }

    @Builder
    public Order(
            Long userId,
            String payMethod,
            String receiverName,
            String receiverPhone,
            String receiverZipcode,
            String receiverAddress1,
            String receiverAddress2,
            String etcMessage
    ) {
        if (userId == null) throw new InvalidParamException("Order.userId");
        if (StringUtils.isBlank(payMethod)) throw new InvalidParamException("Order.payMethod");
        if (StringUtils.isBlank(receiverName)) throw new InvalidParamException("Order.receiverName");
        if (StringUtils.isBlank(receiverPhone)) throw new InvalidParamException("Order.receiverPhone");
        if (StringUtils.isBlank(receiverZipcode)) throw new InvalidParamException("Order.receiverZipcode");
        if (StringUtils.isBlank(receiverAddress1)) throw new InvalidParamException("Order.receiverAddress1");
        if (StringUtils.isBlank(receiverAddress2)) throw new InvalidParamException("Order.receiverAddress2");
        if (StringUtils.isBlank(etcMessage)) throw new InvalidParamException("Order.etcMessage");

        this.orderToken = TokenGenerator.randomCharacterWithPrefix(ORDER_PREFIX);
        this.userId = userId;
        this.payMethod = payMethod;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverZipcode = receiverZipcode;
        this.receiverAddress1 = receiverAddress1;
        this.receiverAddress2 = receiverAddress2;
        this.etcMessage = etcMessage;
        this.orderedAt = ZonedDateTime.now();
        this.status = Status.INIT;
    }

    public Long calculateTotalAmount() {
        return orderItemList.stream()
                .mapToLong(OrderItem::calculateTotalAmount)
                .sum();
    }

    public void orderComplete() {
        if(this.status != Status.INIT) throw new IllegalStatusException();
        this.status = Status.ORDER_COMPLETE;
    }
}
