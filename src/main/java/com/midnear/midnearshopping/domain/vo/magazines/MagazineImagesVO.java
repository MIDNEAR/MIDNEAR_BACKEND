package com.midnear.midnearshopping.domain.vo.magazines;

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
}
