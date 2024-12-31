package com.midnear.midnearshopping.domain.dto.member;

import com.midnear.midnearshopping.domain.vo.member.MemberVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
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

    public static MemberDto toDto(MemberVO member) {
        return new MemberDto(
                member.getUserId(),
                member.getName(),
                member.getId(),
                member.getPassword(),
                member.getPhoneNumber(),
                member.getEmail()
        );
    }

}
