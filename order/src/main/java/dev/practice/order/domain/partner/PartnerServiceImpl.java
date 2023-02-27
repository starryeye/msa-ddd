package dev.practice.order.domain.partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService{

    private final PartnerStore partnerStore;
    private final PartnerReader partnerReader;

    @Override
    @Transactional
    public PartnerInfo registerPartner(PartnerCommand command) {
        //1. command 로 넘어온 애를 비영속 partner 로 만들어 내기
        //2. 비영속 partner 를 save to db
        //3. 영속 partner 를 partnerInfo 로 만들어 리턴

        var initPartner = command.toEntity();

        Partner partner = partnerStore.store(initPartner);

        return new PartnerInfo(partner);
    }

    @Override
    @Transactional(readOnly = true)
    public PartnerInfo getPartnerInfo(String partnerToken) {
        //1. token으로 영속 Partner를 읽어온다.
        //2. 영속 Partner로 partnerInfo를 만들어 리턴

        Partner partner = partnerReader.getPartner(partnerToken);
        return new PartnerInfo(partner);
    }

    @Override
    @Transactional
    public PartnerInfo enablePartner(String partnerToken) {
        //1. token으로 영속 partner를 읽어온다.
        //2. partner.enable()

        Partner partner = partnerReader.getPartner(partnerToken);
        partner.enable();

        return new PartnerInfo(partner);
    }

    @Override
    @Transactional
    public PartnerInfo disablePartner(String partnerToken) {

        Partner partner = partnerReader.getPartner(partnerToken);
        partner.disable();

        return new PartnerInfo(partner);
    }
}
