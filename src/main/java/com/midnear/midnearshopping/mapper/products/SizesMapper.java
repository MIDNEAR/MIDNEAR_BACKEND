package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.vo.products.SizesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SizesMapper {
    void registerProducts(SizesVo sizesVo);

    List<SizesVo> getSizesByProductColorsId(Long productColorId);

    boolean existsBySizeId(Long sizeId);

    void updateSize(SizesVo sizesVo);

    void deleteSize(Long sizeId);
}
