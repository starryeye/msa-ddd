package dev.practice.gift.application.gift;

import dev.practice.gift.domain.gift.GiftCommand;
import dev.practice.gift.domain.gift.GiftInfo;
import dev.practice.gift.domain.gift.GiftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftFacade {

    private final GiftService giftService;

    /**
     * 선물 토큰으로 선물 주문 정보를 얻는다.
     * 선물 수령자의 수락 페이지 로딩 시 사용.
     */
    public GiftInfo getOrder(String giftToken) {
        log.info("getOrder giftToken={}", giftToken);

        return giftService.getGiftInfo(giftToken);
    }

    /**
     * 선물 주문 등록
     */
    public GiftInfo registerOrder(GiftCommand.Register command) {

        var giftInfo = giftService.registerOrder(command);
        log.info("registerOrder orderToken={}", giftInfo.getOrderToken());
        return giftInfo;
    }

    /**
     * 선물 주문 상태 값, 결제 중으로 변경
     */
    public void requestPaymentProcessing(String giftToken) {
        giftService.requestPaymentProcessing(giftToken);
    }

    /**
     * 선물 주문 상태 값, 결제 완료로 변경
     */
    public void completePayment(String orderToken) {
        giftService.completePayment(orderToken);
    }

    /**
     * 선물 주문 상태 값, 선물 수락으로 변경
     * 주문 배송지 정보 업데이트
     */
    public void acceptGift(GiftCommand.Accept command) {
        giftService.acceptGift(command);
    }
}
