package dev.practice.gift.interfaces.api.gift;

import dev.practice.gift.domain.gift.GiftCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface GiftDtoMapper {

    //선물 주문 등록 요청
    GiftCommand.Register of(GiftDto.RegisterReq request);
    GiftCommand.RegisterOrderItem of(GiftDto.RegisterOrderItemReq request);
    GiftCommand.RegisterOrderItemOptionGroup of(GiftDto.RegisterOrderItemOptionGroupReq request);
    GiftCommand.RegisterOrderItemOption of(GiftDto.RegisterOrderItemOptionReq request);

    //수령자 배송지 정보 업데이트
    GiftCommand.Accept of(String giftToken, GiftDto.AcceptGiftReq request);
}
