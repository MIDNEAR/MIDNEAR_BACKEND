package com.midnear.midnearshopping.mapper.claim;

import com.midnear.midnearshopping.domain.vo.claim.CanceledProductVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCanceledProductMapper {
    void insertCanceledProduct(CanceledProductVO canceledProductVO);
}