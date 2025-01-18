package com.midnear.midnearshopping.domain.dto.users;

import com.midnear.midnearshopping.domain.vo.users.UsersVO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoChangeDto {

    private String name;
    private String id;
    private String phoneNumber;
    private String email;
    private String socialType;

    public static UserInfoChangeDto toDto(UsersVO user) {
        return new UserInfoChangeDto(
                user.getName(),
                user.getId(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getSocialType()
        );
    }
}
