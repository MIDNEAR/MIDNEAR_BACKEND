package com.midnear.midnearshopping.domain.dto.products;

import com.midnear.midnearshopping.domain.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductColorsDto {
    private Long productColorId;
    //private Long coordinatedProductId;
    private String color;
    private  Long productId;
    private List<SizesDto> sizes  = new ArrayList<>();
    private List<MultipartFile> productImages  = new ArrayList<>();
    private ProductStatus saleStatus;
}
