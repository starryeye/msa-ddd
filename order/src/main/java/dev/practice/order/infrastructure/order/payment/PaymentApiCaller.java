package dev.practice.order.infrastructure.order.payment;

import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.payment.PayMethod;

public interface PaymentApiCaller {

    //결제 처리 가능 여부
    boolean support(PayMethod payMethod);

    //결제 처리
    void pay(OrderCommand.PaymentRequest request);
}
