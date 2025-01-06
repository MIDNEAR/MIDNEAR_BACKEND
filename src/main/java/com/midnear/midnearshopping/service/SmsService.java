package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.config.SmsCertificationUtil;
import com.midnear.midnearshopping.domain.dto.users.SmsRequestDto;
import com.midnear.midnearshopping.domain.dto.users.SmsVerifyDto;
import com.midnear.midnearshopping.domain.vo.users.SmsRepository;
import com.midnear.midnearshopping.domain.vo.users.UsersVO;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import kotlinx.serialization.Required;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final SmsCertificationUtil smsCertificationUtil;
    private final SmsRepository smsRepository;
    private final UsersMapper userMapper;

    public void sendSms(SmsRequestDto smsRequestDto) {
        String phoneNum = smsRequestDto.getPhoneNum();
        if(userMapper.isMemberExistByPhone(phoneNum)) {
            throw new IllegalArgumentException("이미 가입된 번호입니다. 로그인 해 주세요.");
            // 소셜로그인 도입 한 후에 소셜로그인 유저일경우는 다르게 처리 해야할 수도... ..
        }

        String certificationCode = Integer.toString((int)(Math.random()*(999999 - 100000 + 1)) + 100000); //6자리 랜덤 난수 생성
        smsCertificationUtil.sendSMS(phoneNum, certificationCode);
        smsRepository.createSmsCertification(phoneNum, certificationCode);
    }

    public Boolean verifyCode(SmsVerifyDto smsVerifyDto) {
        if (isVerify(smsVerifyDto.getPhoneNum(), smsVerifyDto.getCertificationCode())) { // 인증 코드 검증
            smsRepository.deleteSmsCertification(smsVerifyDto.getPhoneNum()); // 검증이 성공하면 Redis에서 인증 코드 삭제
            return true; // 인증 성공 반환
        } else {
            return false; // 인증 실패 반환
        }
    }

    // 전화번호와 인증 코드를 검증하는 메서드
    public boolean isVerify(String phoneNum, String certificationCode) {
        return smsRepository.hasKey(phoneNum) && // 전화번호에 대한 키가 존재하고
                smsRepository.getSmsCertification(phoneNum).equals(certificationCode); // 저장된 인증 코드와 입력된 인증 코드가 일치하는지 확인
    }

}
