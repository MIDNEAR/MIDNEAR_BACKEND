package com.midnear.midnearshopping.mapper.disruptive;

import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveDTO;
import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DisruptiveMapper {
//  제한할 아이디 검색
    List<String> searchId(String id);

//  제한할 아이디 등록
    void insertDisruptive(disruptiveDTO disruptiveDTO);

//  전체, 최신순 페이징
    List<disruptiveListDTO> SelectDisruptlist(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int count();

//  검색
    List<disruptiveListDTO> disruptiveSearchInquiries(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("dateFilter")String dateFilter, @Param("orderBy")String orderBy, @Param("search")String search ,@Param("searchValue")String searchValue);
    int searchCount(@Param("dateFilter")String dateFilter, @Param("orderBy")String orderBy, @Param("search")String search, @Param("searchValue")String searchValue);

//  판매방해 고객 제한 해제
    void deleteDisrupt(int disruptiveCustomerId);
}
