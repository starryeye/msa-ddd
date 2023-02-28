package dev.practice.order.interfaces.partner;

import dev.practice.order.application.partner.PartnerFacade;
import dev.practice.order.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partners")
public class PartnerApiController {

    private final PartnerFacade partnerFacade;

    @PostMapping
    public CommonResponse registerPartner(@RequestBody @Valid PartnerDto.RegisterRequest request) {
        //1. 외부에서 전달된 파라미터(dto) 를 Command, Criteria 로 convert
        //2. facade 호출해서 PartnerInfo 리턴받기
        //3. PartnerInfo 를 CommonResponse 로 변환해서 리턴하기

        var command = request.toCommand();

        var partnerInfo = partnerFacade.registerPartner(command);

        var response = new PartnerDto.RegisterResponse(partnerInfo);

        return CommonResponse.success(response);
    }
}
