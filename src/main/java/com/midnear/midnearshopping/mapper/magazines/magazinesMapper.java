package com.midnear.midnearshopping.mapper.magazines;

import com.midnear.midnearshopping.domain.dto.magazines.MagazineImagesDTO;
import com.midnear.midnearshopping.domain.dto.magazines.MagazinesDTO;
import com.midnear.midnearshopping.domain.dto.magazines.MagazinesListDTO;
import com.midnear.midnearshopping.domain.vo.magazines.MagazineImagesVO;
import com.midnear.midnearshopping.domain.vo.magazines.MagazinesVO;
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

//  삭제기능
    void deleteMagazines(int magazineId);

//  매거진 게시글 등록
    void insertMagazine(MagazinesVO magazinesVO);

//  매거진 이미지 등록
    void uploadMagazineImages(MagazineImagesVO magazineImagesVO);

//  작성한 매거진 가져오기
    MagazinesDTO selectMagazine(Long magazineId);

//  작성한 매거진 수정하기
    void updateMagazine(MagazinesVO magazinesVO);

//  저장된 매거진 이미지 가져오기
    List<MagazineImagesDTO> selectMagazineImage(Long magazineId);

//  수정할 매거진 이미지 삭제
    void deleteMagazineImage(Long magazineId);
}
