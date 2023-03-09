package dev.practice.gift.interfaces.api.gift;

import dev.practice.gift.application.gift.GiftFacade;
import dev.practice.gift.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gifts")
public class GiftApiController {

    private final GiftFacade giftFacade;
    private final GiftDtoMapper giftDtoMapper;

    //선물 조회
    @GetMapping("/{giftToken}")
    public CommonResponse<?> retrieveOrder(@PathVariable String giftToken) {
        var giftInfo = giftFacade.getOrder(giftToken);
        return CommonResponse.success(giftInfo);
    }

    //선물 주문 등록 요청
    @PostMapping
    public CommonResponse<?> registerOrder(@RequestBody @Valid GiftDto.RegisterReq request) {
        var command = giftDtoMapper.of(request);
        var giftInfo = giftFacade.registerOrder(command);
        return CommonResponse.success(new GiftDto.RegisterRes(giftInfo));
    }

    //선물 주문 상태를 결제 중 상태로 변경
    @PostMapping("/{giftToken}/payment-processing")
    public CommonResponse<?> requestPaymentProcessing(@PathVariable String giftToken) {
        giftFacade.requestPaymentProcessing(giftToken);
        return CommonResponse.success("OK");
    }

    //선물 주문 상태를 선물 수락 상태로 변경, 선물 주문 배송지 정보 업데이트
    @PostMapping("/{giftToken}/accept-gift")
    public CommonResponse<?> acceptGift(
            @PathVariable String giftToken,
            @RequestBody @Valid GiftDto.AcceptGiftReq request
    ) {
        var acceptCommand = giftDtoMapper.of(giftToken, request);
        giftFacade.acceptGift(acceptCommand);
        return CommonResponse.success("OK");
    }
}
