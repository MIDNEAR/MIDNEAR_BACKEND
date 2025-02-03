package com.midnear.midnearshopping.domain.dto.products;

import com.midnear.midnearshopping.domain.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductDetailColorDto {
    private Long productColorId;
    private String color;
    private Long productId;
    private String mainImage;
    private List<ProductDetailSizeDto> sizes = new ArrayList<>();
    private ProductStatus saleStatus;
}