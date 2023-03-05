package dev.practice.order.domain.order.payment.validator;

import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import org.springframework.stereotype.Component;

@org.springframework.core.annotation.Order(value = 30)
@Component
public class PayStatusValidator implements PaymentValidator{
    @Override
    public void validate(Order order, OrderCommand.PaymentRequest paymentRequest) {

        // 이미 결제 완료된 주문인데 또 결제 요청이 오면 예외
        if(order.isAlreadyPaymentComplete())
            throw new InvalidParamException("이미 결제완료된 주문입니다.");
    }
}
