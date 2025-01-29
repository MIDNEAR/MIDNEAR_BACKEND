package com.midnear.midnearshopping.mapper.storeManagement;

import com.midnear.midnearshopping.domain.vo.storeImages.StoreImagesVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreImagesMapper {
    StoreImagesVo findMainImage();
    StoreImagesVo findLogoImage();
    void deleteImageById(Long id);
    void uploadImage(StoreImagesVo storeImagesVo);

}
