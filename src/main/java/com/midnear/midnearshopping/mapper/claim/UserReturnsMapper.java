package com.midnear.midnearshopping.mapper.claim;

import com.midnear.midnearshopping.domain.vo.claim.ReturnsVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserReturnsMapper {
    void insertReturn(ReturnsVO returnsVO);
}