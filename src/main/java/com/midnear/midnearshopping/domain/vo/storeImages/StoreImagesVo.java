package com.midnear.midnearshopping.domain.vo.storeImages;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class StoreImagesVo {
    private Long imageId;
    private String imageUrl;
    private Long fileSize;
    private String extension;
    private Date creationDate;
    private String type;
}
