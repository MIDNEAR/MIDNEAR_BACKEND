package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.CancelProductDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CancelProductMapper {

//  전체 최신순 필터링
    List<CancelProductDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int count();

// 필터링 검색
    List<CancelProductDTO>filterSearch(ParamDTO paramDTO);
    int filterCount(ParamDTO paramDTO);

// 선택 상품 취소처리
    void confirmCancel(List<Long> canceledProductId);

// 선택 상품 취소거부 처리
    void denayCancel(List<Long> canceledProductId);
}
