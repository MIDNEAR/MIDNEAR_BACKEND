package com.midnear.midnearshopping.domain.dto.users;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerifyDto {
    @NotNull(message = "이메일을 입력해주세요.")
    private String email;
    @NotNull(message = "인증번호를 입력해주세요.")
    private String certificationCode;
}
