package com.midnear.midnearshopping.domain.vo.magazines;

import com.midnear.midnearshopping.domain.dto.magazines.MagazineImagesDTO;
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
public class MagazineImagesVO {
    Long magazineImageId;
    String imageUrl;
    Long fileSize;
    String extension;
    Date creationDate;
    Long magazineId;

    public static MagazineImagesVO toVO(MagazineImagesDTO magazineImagesDTO) {
        return MagazineImagesVO.builder()
                .imageUrl(magazineImagesDTO.getImageUrl())
                .fileSize(magazineImagesDTO.getFileSize())
                .extension(magazineImagesDTO.getExtension())
                .magazineId(magazineImagesDTO.getMagazineId())
                .build();
    }
}
