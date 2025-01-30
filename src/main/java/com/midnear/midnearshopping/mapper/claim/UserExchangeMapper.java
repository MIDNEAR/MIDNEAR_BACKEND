package com.midnear.midnearshopping.mapper.claim;

import com.midnear.midnearshopping.domain.vo.claim.ExchangeVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserExchangeMapper {
    void insertExchange(ExchangeVO exchangeVO);
}