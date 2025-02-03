package com.midnear.midnearshopping.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.midnear.midnearshopping.domain.dto.users.SocialUserInfoDto;
import com.midnear.midnearshopping.domain.dto.users.UsersDto;
import com.midnear.midnearshopping.domain.vo.users.UsersVO;
import com.midnear.midnearshopping.jwt.JwtUtil;
import com.midnear.midnearshopping.mapper.users.UsersMapper;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UsersMapper usersMapper;
    private final JwtUtil jwtUtil;
    private final OAuthKaKaoProvider kakaoOAuthProvider;
    private final OAuthGoogleProvider googleOAuthProvider;
    private final OAuthNaverProvider naverOAuthProvider;

    public String kakaoLogin(String code) throws JsonProcessingException, IllegalAccessException {
        String accessToken = kakaoOAuthProvider.getAccessToken(code);
        Map<String, Object> userInfo = kakaoOAuthProvider.getUserInfo(accessToken);
        SocialUserInfoDto user = extractKakaoUserInfo(userInfo);
        return userLogin(user, "kakao");
    }

    public String googleLogin(String code) throws JsonProcessingException, IllegalArgumentException {
        String accessToken = googleOAuthProvider.getAccessToken(code);
        Map<String, Object> userInfo = googleOAuthProvider.getUserInfo(accessToken);
        SocialUserInfoDto user = extractGoogleUserInfo(userInfo);
        return userLogin(user, "google");
    }

    public String naverLogin(String code, String state) throws JsonProcessingException, IllegalArgumentException {
        String accessToken = naverOAuthProvider.getAccessToken(code, state);
        Map<String, Object> userInfo = naverOAuthProvider.getUserInfo(accessToken);
        SocialUserInfoDto user = extractNaverUserInfo(userInfo);
        return userLogin(user, "naver");
    }

    private String userLogin(SocialUserInfoDto userInfo, String provider) throws IllegalArgumentException {
        UsersVO user = usersMapper.getMemberByEmail(userInfo.getEmail());

        if (user == null) { // 회원가입
            user = new UsersVO();
            user.setName(userInfo.getName());
            user.setId(userInfo.getId());
            user.setEmail(userInfo.getEmail());
            user.setPhoneNumber(userInfo.getPhone());
            user.setWithdrawn("Y"); // 기본값 설정 (추후 논의)
            user.setSocialType(provider);
            user.setPointBalance(BigDecimal.valueOf(0));
            usersMapper.createMember(user);
        }
        if(!Objects.equals(user.getSocialType(), provider)){
            throw new IllegalArgumentException("다른 소셜 계정 혹은 일반 로그인에 등록된 이메일입니다.");
        }

        // JWT 토큰 생성 및 반환
        return jwtUtil.createAccessToken(UsersDto.toDto(user));
    }
    public SocialUserInfoDto extractKakaoUserInfo(Map<String, Object> userInfo) {
        String rawPhone = userInfo.containsKey("phone") ? userInfo.get("phone").toString() : null;
        String formattedPhone = (rawPhone != null) ? rawPhone.replace("-", "") : null; // "-" 제거

        return SocialUserInfoDto.builder()
                .id(userInfo.get("id").toString())
                .email(userInfo.containsKey("email") ? userInfo.get("email").toString() : null)
                .name(userInfo.containsKey("nickname") ? userInfo.get("nickname").toString() : null)
                .phone(formattedPhone) // 포맷팅된 전화번호 사용
                .nickname(userInfo.containsKey("nickname") ? userInfo.get("nickname").toString() : null)
                .build();
    }

    public SocialUserInfoDto extractGoogleUserInfo(Map<String, Object> userInfo) {
        return SocialUserInfoDto.builder()
                .id(userInfo.get("sub").toString()) // 구글은 "sub" 필드가 사용자 ID
                .email(userInfo.get("email").toString())
                .name(userInfo.getOrDefault("name", "Unknown").toString())
                .phone(null) // 구글 API에서 기본적으로 전화번호는 제공하지 않음... 하 이거 따로 받아야할거같은데요 sibal jinjja johnaj jangna...
                .nickname(null) // 구글은 닉네임 정보 없음
                .build();
    }

    public SocialUserInfoDto extractNaverUserInfo(Map<String, Object> userInfo) {
        Map<String, Object> response = (Map<String, Object>) userInfo.get("response"); // 네이버는 "response" 내부에 데이터가 있음
        String rawPhone = response.getOrDefault("mobile", "").toString(); // 원래의 전화번호
        String formattedPhone = rawPhone.replace("-", ""); // "-" 제거

        return SocialUserInfoDto.builder()
                .id(response.get("id").toString())
                .email(response.get("email").toString())
                .name(response.get("name").toString())
                .phone(formattedPhone) // 포맷팅된 전화번호 사용
                .nickname(response.getOrDefault("nickname", null).toString())
                .build();
    }

}
