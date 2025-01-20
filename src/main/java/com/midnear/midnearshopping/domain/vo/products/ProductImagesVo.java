package com.midnear.midnearshopping.domain.vo.products;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ProductImagesVo {
    private Long productImageId;
    private String imageUrl;
    private Long fileSize;
    private Long productColorId;
    private String extension;
    private Date imageCreatedDate;
}
