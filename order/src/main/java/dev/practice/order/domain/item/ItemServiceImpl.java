package dev.practice.order.domain.item;

import dev.practice.order.domain.partner.PartnerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final PartnerReader partnerReader;
    private final ItemStore itemStore;
    private final ItemReader itemReader;
    private final ItemOptionGroupStore itemOptionGroupStore;
    private final ItemOptionStore itemOptionStore;

    @Override
    public String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken) {

        //1. get partnerId
        //타 Aggregate (partner) 와의 참조는 token 으로 연결하는게 가독성과 보안에 좋다.
        var partner = partnerReader.getPartner(partnerToken);
        var partnerId = partner.getId();

        //2. item store
        var initItem = command.toEntity(partnerId);
        var item = itemStore.store(initItem);

        //3. itemOptionGroup + itemOption store
        //4. return itemToken
    }

    @Override
    public void changeOnSale(String itemToken) {

    }

    @Override
    public void changeEndOfSale(String itemToken) {

    }

    @Override
    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        return null;
    }
}
