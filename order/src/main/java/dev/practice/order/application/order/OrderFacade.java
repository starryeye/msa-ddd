package dev.practice.order.application.order;

import dev.practice.order.domain.notification.NotificationService;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.OrderInfo;
import dev.practice.order.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final NotificationService notificationService;

    /**
     * 결제 직후 문자 발송을 facade 에 넣을래 domain 에 넣을래.. 이는 상황에 따라 다르다.
     * 결제 직후 문자 발송이 필수라면 도메인에 넣고 아니면 넣지 않는다. 보통은 필수가 아니다.
     * 도메인은 tx 로 묶인다. (tx 로 묶였다는 건 All or Nothing 의 핵심 도메인 지식이라는 것)
     * tx 로 안 묶인다는 것은 핵심 도메인 지식이 아니라는 것이다.
     * -> 문자 발송은 도메인 지식이 아니다.
     * -> 문자 발송이 안되서 주문 등록이 취소 되는 건 아니자나..
     * -> 따라서 tx 가 없는 layer 인 Application 에서 분리
     */
    public String registerOrder(OrderCommand.RegisterOrder registerOrder) {

        var orderToken = orderService.registerOrder(registerOrder);

        notificationService.sendKakao(null, null);

        return orderToken;
    }

    public OrderInfo.Main retrieveOrder(String orderToken) {
        return orderService.retrieveOrder(orderToken);
    }

    public void paymentOrder(OrderCommand.PaymentRequest paymentRequest) {
        orderService.paymentOrder(paymentRequest);
        notificationService.sendKakao(null, null);
    }

    public void updateReceiverInfo(String orderToken, OrderCommand.UpdateReceiverInfoRequest orderCommand) {
        orderService.updateReceiverInfo(orderToken, orderCommand);
        notificationService.sendKakao(null, null);
    }
}
