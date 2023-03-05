package dev.practice.order.domain.order.payment.validator;

import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import org.springframework.stereotype.Component;

@org.springframework.core.annotation.Order(value = 10)
@Component
public class PayAmountValidator implements PaymentValidator{
    @Override
    public void validate(Order order, OrderCommand.PaymentRequest paymentRequest) {

        //주문가격 10000 원 인데.. 결제금액 1000 원 이면 예외
        if(!order.calculateTotalAmount().equals(paymentRequest.getAmount()))
            throw new InvalidParamException("주문가격이 불일치합니다.");
    }
}
