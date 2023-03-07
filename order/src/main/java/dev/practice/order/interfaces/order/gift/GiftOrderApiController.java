package dev.practice.order.interfaces.order.gift;

import dev.practice.order.application.order.OrderFacade;
import dev.practice.order.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gift-orders")
public class GiftOrderApiController {

    private final OrderFacade orderFacade;

    @PostMapping("/{orderToken}/update-receiver-info")
    public CommonResponse<?> updateReceiverInfo(
            @PathVariable String orderToken,
            @RequestBody @Valid GiftOrderDto.UpdateReceiverInfoReq request
    ) {
        var orderCommand = request.toCommand();

        orderFacade.updateReceiverInfo(orderToken, orderCommand);

        return CommonResponse.success("OK");
    }
}
