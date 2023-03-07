package dev.practice.order.domain.order.gift;

import dev.practice.order.common.exception.IllegalStatusException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.OrderReader;
import dev.practice.order.domain.order.payment.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftOrderServiceImpl implements GiftOrderService{

    private final OrderReader orderReader;
    private final PaymentProcessor paymentProcessor;

    private final GiftMessageChannelSender giftMessageChannelSender;

    @Override
    @Transactional
    public void paymentOrder(OrderCommand.PaymentRequest paymentRequest) {
        log.info("GiftOrderService.paymentOrder request = {}", paymentRequest);

        var order = orderReader.getOrder(paymentRequest.getOrderToken());

        //orderComplete 와 중복 로직, 보상트랜잭션 회피 위함
        if(order.getStatus() != Order.Status.INIT)
            throw new IllegalStatusException();

        //주문 결제 처리
        paymentProcessor.pay(order, paymentRequest);

        //주문 결제 완료
        order.orderComplete();

        //주문 결제 완료 메시징
        giftMessageChannelSender.paymentComplete(new GiftPaymentCompleteMessage(order.getOrderToken()));
    }
}
