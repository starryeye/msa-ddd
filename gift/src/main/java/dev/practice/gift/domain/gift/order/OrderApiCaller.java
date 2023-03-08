package dev.practice.gift.domain.gift.order;

import dev.practice.gift.domain.gift.GiftCommand;

public interface OrderApiCaller {

    String registerGiftOrder(OrderApiCommand.Register request);

    void updatedReceiverInfo(String orderToken, GiftCommand.Accept request);
}
