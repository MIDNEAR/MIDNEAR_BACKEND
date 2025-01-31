package com.midnear.midnearshopping.domain.dto.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.midnear.midnearshopping.domain.enums.ProductStatus;
import com.midnear.midnearshopping.domain.vo.products.ProductColorsVo;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductColorsDto {
    private Long productColorId;
    private String color;
    private  Long productId;
    private List<SizesDto> sizes  = new ArrayList<>();
    @JsonIgnore
    private List<MultipartFile> productImages  = new ArrayList<>();
    private ProductStatus saleStatus;
    @Builder.Default
    private List<String> ImageUrls = new ArrayList<>(); // 이미지 링크

    public static ProductColorsDto toDto(ProductColorsVo productColorsVo) {
        return ProductColorsDto.builder()
                .productColorId(productColorsVo.getProductColorId())
                .color(productColorsVo.getColor())
                .productId(productColorsVo.getProductId())
                .saleStatus(productColorsVo.getSaleStatus())
                .build();
    }
}
