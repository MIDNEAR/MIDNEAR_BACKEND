package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.vo.products.SizesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SizesMapper {
    void registerProducts(SizesVo sizesVo);

    List<SizesVo> getSizesByProductColorsId(Long productColorId);

    boolean existsBySizeId(Long sizeId);

    void updateSize(SizesVo sizesVo);

    void deleteSize(Long sizeId);
    void updateSizeByColorAndSize(@Param("productColorId") Long productColorId, @Param("size") String size, @Param("quantity") int quantity);
    int getStockByColorAndSize(@Param("productColorId") Long productColorId, @Param("size") String size);
}
