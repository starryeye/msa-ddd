package dev.practice.order.domain.order.payment.validator;

import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.payment.PayMethod;
import org.springframework.stereotype.Component;

@org.springframework.core.annotation.Order(value = 40)
@Component
public class FdsPaymentValidator implements PaymentValidator{
    @Override
    public void validate(Order order, OrderCommand.PaymentRequest paymentRequest) {
        if(paymentRequest.getPayMethod() == PayMethod.OVERSEA)
            throw new InvalidParamException("해외카드는 결제 불가능합니다.");
    }
}
