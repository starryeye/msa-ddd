package dev.practice.order.interfaces.order.gift;

import dev.practice.order.domain.order.OrderCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface GiftOrderDtoMapper {

    //선물 주문 등록
    @Mapping(source = "buyerUserId", target = "userId")
    OrderCommand.RegisterOrder of(GiftOrderDto.RegisterOrderRequest request);
    OrderCommand.RegisterOrderItem of(GiftOrderDto.RegisterOrderItemRequest request);
    OrderCommand.RegisterOrderItemOptionGroup of(GiftOrderDto.RegisterOrderItemOptionGroupRequest request);
    OrderCommand.RegisterOrderItemOption of(GiftOrderDto.RegisterOrderItemOptionRequest request);
    GiftOrderDto.RegisterResponse of(String orderToken);

    //선물 주문 결제 요청
    OrderCommand.PaymentRequest of(GiftOrderDto.PaymentRequest request);

}
