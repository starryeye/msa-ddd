package dev.practice.gift.domain.gift;

import dev.practice.gift.domain.gift.order.OrderApiCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService{

    private final GiftReader giftReader;
    private final GiftStore giftStore;
    private final OrderApiCaller orderApiCaller;
    private final GiftToOrderMapper giftToOrderMapper;

    /**
     * 선물 토큰으로 선물 정보를 얻는다.
     * 선물 수령자의 수락 페이지 로딩 시 사용.
     */
    @Override
    @Transactional(readOnly = true)
    public GiftInfo getGiftInfo(String giftToken) {
        var gift = giftReader.getGiftBy(giftToken);
        return new GiftInfo(gift);
    }

    /**
     * 선물 주문 등록
     * - 주문 서비스 통신
     * - 선물 정보 DB 반영
     */
    @Override
    @Transactional
    public GiftInfo registerOrder(GiftCommand.Register request) {

        var orderApiCommand = giftToOrderMapper.of(request);

        //주문 서비스 통신, 결과 값 orderToken
        var orderToken = orderApiCaller.registerGiftOrder(orderApiCommand);

        //orderToken 값과 주문 정보 비 영속 엔티티로 변환
        var initGift = request.toEntity(orderToken);

        //DB에 주문 정보 저장
        var gift = giftStore.store(initGift);

        return new GiftInfo(gift);
    }

    /**
     * DB 선물 정보의 상태 값을 IN_PAYMENT 로 변경
     */
    @Override
    @Transactional
    public void requestPaymentProcessing(String giftToken) {
        var gift = giftReader.getGiftBy(giftToken);
        gift.inPayment();
    }

    /**
     * 주문 서비스에서 결재 완료 후 orderToken 을 메시징으로 발행하면..
     * 선물 서비스에서 읽고 DB 선물 정보의 status 값을 ORDER_COMPLETE 로 변경
     */
    @Override
    @Transactional
    public void completePayment(String orderToken) {
        var gift = giftReader.getGiftByOrderToken(orderToken);
        gift.completePayment();
    }

    /**
     * 선물 수령자가 배송지를 입력하고 선물 수락을 하면..
     * - 선물 정보 status 를 ACCEPT 로 변경
     * - 주문 서비스 통신, 주문 배송지 정보 업데이트
     */
    @Override
    @Transactional
    public void acceptGift(GiftCommand.Accept request) {

        var giftToken = request.getGiftToken();
        var gift = giftReader.getGiftBy(giftToken);

        gift.accept(request);

        //주문 서비스 통신, 수령자 배송지 업데이트
        orderApiCaller.updatedReceiverInfo(gift.getOrderToken(), request);
    }
}
