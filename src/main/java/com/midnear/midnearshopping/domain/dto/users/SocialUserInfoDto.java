package com.midnear.midnearshopping.domain.dto.users;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SocialUserInfoDto {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String nickname;
}
