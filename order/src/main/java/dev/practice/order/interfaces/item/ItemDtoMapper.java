package dev.practice.order.interfaces.item;

import dev.practice.order.domain.item.ItemCommand;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ItemDtoMapper {

    //register
    @Mappings({@Mapping(source = "itemOptionGroupRequestDtoForRegisterList", target = "itemOptionGroupRequestList")})
    ItemCommand.RegisterItemRequest of(ItemDto.ItemRequestDtoForRegister requestDto);

    @Mappings({@Mapping(source = "itemOptionRequestDtoForRegisterList", target = "itemOptionRequestList")})
    ItemCommand.RegisterItemOptionGroupRequest of(ItemDto.ItemOptionGroupRequestDtoForRegister requestDto);

    ItemCommand.RegisterItemOptionRequest of(ItemDto.ItemOptionRequestDtoForRegister requestDto);

    ItemDto.ItemTokenResponseDtoForRegister of(String itemToken);
}
