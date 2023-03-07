package dev.practice.order.interfaces.order.gift;

import dev.practice.order.domain.order.OrderCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class GiftOrderDto {

    @Getter
    @Setter
    @ToString
    public static class UpdateReceiverInfoReq {

        private String receiverName;
        private String receiverPhone;
        private String receiverZipcode;
        private String receiverAddress1;
        private String receiverAddress2;
        private String etcMessage;

        public OrderCommand.UpdateReceiverInfoRequest toCommand() {
            return OrderCommand.UpdateReceiverInfoRequest.builder()
                    .receiverName(receiverName)
                    .receiverPhone(receiverPhone)
                    .receiverZipcode(receiverZipcode)
                    .receiverAddress1(receiverAddress1)
                    .receiverAddress2(receiverAddress2)
                    .etcMessage(etcMessage)
                    .build();
        }
    }
}
