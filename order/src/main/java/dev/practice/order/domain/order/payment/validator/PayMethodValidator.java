package dev.practice.order.domain.order.payment.validator;

import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import org.springframework.stereotype.Component;

@org.springframework.core.annotation.Order(value = 20)
@Component
public class PayMethodValidator implements PaymentValidator{

    @Override
    public void validate(Order order, OrderCommand.PaymentRequest paymentRequest) {

        //유저가 선택한 결제 수단은 Toss 인데.. 결제 요청은 Kakao pay 이면 예외
        if(!order.getPayMethod().equals(paymentRequest.getPayMethod().name()))
            throw new InvalidParamException("주문 과정에서의 결제수단이 다릅니다.");
    }
}
