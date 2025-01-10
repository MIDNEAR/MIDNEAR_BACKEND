package com.midnear.midnearshopping.domain.vo.magazines;

import com.midnear.midnearshopping.domain.dto.magazines.MagazinesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagazinesVO {
    Long magazineId;
    String title;
    String content;
    Date createdDate;
    Long viewCount;
    String mainImage;

    public static MagazinesVO toVO(MagazinesDTO magazinesDTO) {
        return MagazinesVO.builder()
                .title(magazinesDTO.getTitle())
                .content(magazinesDTO.getContent())
                .mainImage(magazinesDTO.getMainImage())
                .build();
    }

}
