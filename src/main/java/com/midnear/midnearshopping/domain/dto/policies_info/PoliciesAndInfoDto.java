package com.midnear.midnearshopping.domain.dto.policies_info;

import com.midnear.midnearshopping.domain.vo.policies_info.PoliciesAndInfoVo;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoliciesAndInfoDto {
    private String text;
    private String type;

    public static PoliciesAndInfoDto toEntity(PoliciesAndInfoVo policiesAndInfoVo) {
        return PoliciesAndInfoDto.builder()
                .text(policiesAndInfoVo.getText())
                .type(policiesAndInfoVo.getType())
                .build();
    }
}
