package com.midnear.midnearshopping.domain.vo.users;

import com.midnear.midnearshopping.domain.dto.member.UsersDto;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//INT는 Integer로, Decimal은 BigDecimal로 매핑이 권장된다길래 그리 했습니다.
//코드 리뷰시 이렇게 진행해도 괜찮을지 코멘트 부탁드려요

public class UsersVO {
    private Integer userId;
    private String name;
    private String id;
    private String password;
    private String phoneNumber;
    private String email;
    private String withdrawn= "N";
    private BigDecimal pointBalance;

    public static UsersVO toEntity(UsersDto memberDto) {
        return UsersVO.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .password(memberDto.getPassword())
                .phoneNumber(memberDto.getPhoneNumber())
                .email(memberDto.getEmail())
                .pointBalance(BigDecimal.valueOf(0))
                .build();
    }
}

