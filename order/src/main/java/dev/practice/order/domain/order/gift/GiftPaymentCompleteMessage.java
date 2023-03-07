package dev.practice.order.domain.order.gift;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class GiftPaymentCompleteMessage {
    private final String orderToken;
}
