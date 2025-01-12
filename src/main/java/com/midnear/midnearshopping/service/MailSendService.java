package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.config.EmailCertificationUtil;
import com.midnear.midnearshopping.domain.dto.users.EmailRequestDto;
import com.midnear.midnearshopping.domain.dto.users.EmailVerifyDto;
import com.midnear.midnearshopping.domain.vo.users.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;

@RequiredArgsConstructor
@Service
public class MailSendService {

    private final EmailRepository emailRepository;
    private final EmailCertificationUtil emailCertificationUtil;

    public void sendEmail(EmailRequestDto emailRequestDto) throws MessagingException {
        String email = emailRequestDto.getEmail();

        String certificationCode = Integer.toString((int)(Math.random()*(999999 - 100000 + 1)) + 100000); //6자리 랜덤 난수 생성
        emailCertificationUtil.sendEmail(email, certificationCode);
        emailRepository.createEmailCertification(email, certificationCode);
    }

    public Boolean verifyCode(EmailVerifyDto emailVerifyDto) {
        if (isVerify(emailVerifyDto.getEmail(), emailVerifyDto.getCertificationCode())) { // 인증 코드 검증
            emailRepository.deleteEmailCertification(emailVerifyDto.getEmail()); // 검증이 성공하면 Redis에서 인증 코드 삭제
            return true; // 인증 성공 반환
        } else {
            return false; // 인증 실패 반환
        }
    }

    // 메일과 인증 코드를 검증하는 메서드
    private boolean isVerify(String email, String certificationCode) {
        return emailRepository.hasKey(email) && // 이메일에 대한 키가 존재하고
                emailRepository.getEmailCertification(email).equals(certificationCode); // 저장된 인증 코드와 입력된 인증 코드가 일치하는지 확인
    }
}