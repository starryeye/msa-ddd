package dev.practice.order.domain.order;

import com.google.common.collect.Lists;
import dev.practice.order.common.exception.IllegalStatusException;
import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.common.util.TokenGenerator;
import dev.practice.order.domain.AbstractEntity;
import dev.practice.order.domain.order.fragment.DeliveryFragment;
import dev.practice.order.domain.order.item.OrderItem;
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
@Table(name = "orders")
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

    @Embedded
    private DeliveryFragment deliveryFragment;

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
            DeliveryFragment deliveryFragment
    ) {
        if (userId == null) throw new InvalidParamException("Order.userId");
        if (StringUtils.isBlank(payMethod)) throw new InvalidParamException("Order.payMethod");
        if (deliveryFragment == null) throw new InvalidParamException("Order.deliveryFragment");

        this.orderToken = TokenGenerator.randomCharacterWithPrefix(ORDER_PREFIX);
        this.userId = userId;
        this.payMethod = payMethod;
        this.deliveryFragment = deliveryFragment;
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

    // TODO: 개별 배송 기능
    public void deliveryPrepare() {
        if (this.status != Status.ORDER_COMPLETE) throw new IllegalStatusException();
        this.status = Status.DELIVERY_PREPARE;
        this.getOrderItemList().forEach(OrderItem::deliveryPrepare);
    }

    // TODO: 개별 배송 기능
    public void inDelivery() {
        if (this.status != Status.DELIVERY_PREPARE) throw new IllegalStatusException();
        this.status = Status.IN_DELIVERY;
        this.getOrderItemList().forEach(OrderItem::inDelivery);
    }

    // TODO: 개별 배송 기능
    public void deliveryComplete() {
        if (this.status != Status.IN_DELIVERY) throw new IllegalStatusException();
        this.status = Status.DELIVERY_COMPLETE;
        this.getOrderItemList().forEach(OrderItem::deliveryComplete);
    }

    public boolean isAlreadyPaymentComplete() {

        switch (this.status) {
            case ORDER_COMPLETE:
            case DELIVERY_PREPARE:
            case IN_DELIVERY:
            case DELIVERY_COMPLETE:
                return true;
        }
        return false;
    }

    public void updateDeliveryFragment(
            String receiverName,
            String receiverPhone,
            String receiverZipcode,
            String receiverAddress1,
            String receiverAddress2,
            String etcMessage
    ) {
        this.deliveryFragment = DeliveryFragment.builder()
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .receiverZipcode(receiverZipcode)
                .receiverAddress1(receiverAddress1)
                .receiverAddress2(receiverAddress2)
                .etcMessage(etcMessage)
                .build();
    }
}
