package com.midnear.midnearshopping.mapper.disruptive;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DisruptiveMapper {
//  제한할 아이디 검색
    List<String> searchId(String id);
}
