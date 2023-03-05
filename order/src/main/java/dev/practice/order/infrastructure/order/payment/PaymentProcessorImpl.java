package dev.practice.order.infrastructure.order.payment;

import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.payment.PaymentProcessor;
import dev.practice.order.domain.order.payment.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor {

    //PaymentApiCaller Bean List DI
    private final List<PaymentApiCaller> paymentApiCallerList;

    //PaymentValidator Bean List DI
    private final List<PaymentValidator> paymentValidatorList;

    @Override
    public void pay(Order order, OrderCommand.PaymentRequest request) {

        //모든 validator 를 돌림.
        paymentValidatorList.forEach(paymentValidator -> paymentValidator.validate(order, request));

        //하나의 ApiCaller(PayMethod, 결제 수단) 를 찾는다
        PaymentApiCaller paymentApiCaller = routingApiCaller(request);
        paymentApiCaller.pay(request);
    }

    private PaymentApiCaller routingApiCaller(OrderCommand.PaymentRequest request) {
        return paymentApiCallerList.stream()
                .filter(paymentApiCaller -> paymentApiCaller.support(request.getPayMethod()))
                .findFirst()
                .orElseThrow(InvalidParamException::new);
    }
}
