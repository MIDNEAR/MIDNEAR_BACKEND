package com.midnear.midnearshopping.mapper.magazines;

import com.midnear.midnearshopping.domain.dto.magazines.MagazinesListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface magazinesMapper {

//  매거진 List 전체 / 최신순 띄우기
    List<MagazinesListDTO> selectMagazineList(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int count();

//  검색기능
    List<MagazinesListDTO> magazineSearch(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("dateFilter")String dateFilter, @Param("orderBy")String orderBy, @Param("search")String search ,@Param("searchValue")String searchValue);
    int searchCount(@Param("dateFilter")String dateFilter, @Param("orderBy")String orderBy, @Param("search")String search, @Param("searchValue")String searchValue);

}
