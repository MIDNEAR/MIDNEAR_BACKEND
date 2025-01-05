package com.midnear.midnearshopping.mapper.disruptive;

import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DisruptiveMapper {
//  제한할 아이디 검색
    List<String> searchId(String id);

//  제한할 아이디 등록
    void insertDisruptive(disruptiveDTO disruptiveDTO);
}
