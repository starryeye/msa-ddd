package dev.practice.order.domain.partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService{

    private final PartnerStore partnerStore;

    @Override
    public PartnerInfo registerPartner(PartnerCommand command) {
        //1. command 로 넘어온 애를 비영속 partner 로 만들어 내기
        //2. 비영속 partner 를 save to db
        //3. 영속 partner 를 partnerInfo 로 만들어 리턴

        var initPartner = command.toEntity();

        Partner partner = partnerStore.store(initPartner);

        return new PartnerInfo(partner);
    }

    @Override
    public PartnerInfo getPartnerInfo(String partnerToken) {
        return null;
    }

    @Override
    public PartnerInfo enablePartner(String partnerToken) {
        return null;
    }

    @Override
    public PartnerInfo disablePartner(String partnerToken) {
        return null;
    }
}
