package com.midnear.midnearshopping.mapper.storeManagement;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FooterMapper {
   void footerUpdate(String footerContents);
}
