package com.midnear.midnearshopping.domain.vo.users;

import com.midnear.midnearshopping.domain.dto.users.UsersDto;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersVO {
    private Integer userId;
    private String name;
    private String id;
    private String password;
    private String phoneNumber;
    private String email;
    private String withdrawn= "N";
    private BigDecimal pointBalance;
    private String socialType;

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

