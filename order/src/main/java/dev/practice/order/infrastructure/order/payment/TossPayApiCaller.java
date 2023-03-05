package dev.practice.order.infrastructure.order.payment;

import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.payment.PayMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TossPayApiCaller implements PaymentApiCaller{
    @Override
    public boolean support(PayMethod payMethod) {
        return PayMethod.TOSS_PAY == payMethod;
    }

    @Override
    public void pay(OrderCommand.PaymentRequest request) {
        //TODO: 외부 PG 통신 실제 구현
    }
}
