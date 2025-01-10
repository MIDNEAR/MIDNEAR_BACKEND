package com.midnear.midnearshopping.domain.dto.users;

import com.midnear.midnearshopping.domain.vo.users.UsersVO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private Integer userId;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "아이디를 입력해주세요")
    private String id;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
    @NotBlank(message = "전화번호를 입력해주세요")
    private String phoneNumber;
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    private String socialType;

    public static UsersDto toDto(UsersVO member) {
        return new UsersDto(
                member.getUserId(),
                member.getName(),
                member.getId(),
                member.getPassword(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getSocialType()
        );
    }

}
