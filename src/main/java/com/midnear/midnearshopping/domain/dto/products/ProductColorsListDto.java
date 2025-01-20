package com.midnear.midnearshopping.domain.dto.products;

import com.midnear.midnearshopping.domain.enums.ProductStatus;
import com.midnear.midnearshopping.domain.vo.products.SizesVo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductColorsListDto {
    private String color;
    private List<SizesVo> sizes;
    private ProductStatus saleStatus;
}
