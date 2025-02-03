package com.midnear.midnearshopping.domain.dto.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductsDto {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int discountRate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String discountStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String discountEndDate;
    private String detail;
    private String sizeGuide;
    private String registeredDate;
    private Long categoryId;
    @Builder.Default // 빌더로 생성 시 List 초기화
    private List<ProductColorsDto> colors = new ArrayList<>();

    public static ProductsDto toDto(ProductsVo productsVo) {
        return ProductsDto.builder()
                .productId(productsVo.getProductId())
                .productName(productsVo.getProductName())
                .price(productsVo.getPrice())
                .discountPrice(productsVo.getDiscountPrice())
                .discountRate(productsVo.getDiscountRate())
                .discountStartDate(String.valueOf(productsVo.getDiscountStartDate()))
                .discountEndDate(String.valueOf(productsVo.getDiscountEndDate()))
                .detail(productsVo.getDetail())
                .sizeGuide(productsVo.getSizeGuide())
                .registeredDate(String.valueOf(productsVo.getRegisteredDate()))
                .categoryId(productsVo.getCategoryId())
                .build();
    }
}
