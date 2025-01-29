package com.midnear.midnearshopping.domain.vo.policies_info;

import com.midnear.midnearshopping.domain.dto.policies_info.PoliciesAndInfoDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PoliciesAndInfoVo {
    private Long id;
    private String text;
    private String type;

    public static PoliciesAndInfoVo toEntity(PoliciesAndInfoDto policiesAndInfoDto) {
        return PoliciesAndInfoVo.builder()
                .id(null)
                .text(policiesAndInfoDto.getText())
                .type(policiesAndInfoDto.getType())
                .build();
    }
}
