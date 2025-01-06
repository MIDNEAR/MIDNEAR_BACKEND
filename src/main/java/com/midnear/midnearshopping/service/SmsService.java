package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.config.SmsCertificationUtil;
import com.midnear.midnearshopping.domain.dto.users.SmsRequestDto;
import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final SmsCertificationUtil smsCertificationUtil;

    public void sendSms(SmsRequestDto smsRequestDto) {
        String phoneNum=smsRequestDto.getPhoneNum();
        String certificationCode = Integer.toString((int)(Math.random()*(999999 - 100000 + 1)) + 100000); //6자리 랜덤 난수 생성
        smsCertificationUtil.sendSMS(phoneNum, certificationCode);
    }

}
