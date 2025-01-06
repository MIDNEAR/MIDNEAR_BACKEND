package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.magazines.MagazineImagesDTO;
import com.midnear.midnearshopping.domain.dto.magazines.MagazinesDTO;
import com.midnear.midnearshopping.domain.dto.magazines.MagazinesListDTO;
import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import com.midnear.midnearshopping.domain.vo.magazines.MagazinesVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MagazinesService {

// 매거진 목록 전체 / 최신순 띄우기
   List<MagazinesListDTO> selectMagazineList(int pageNumber);
   int count();

// 매거진 필터링 검색
   List<MagazinesListDTO> magazineSearch(int pageNumber ,String dateFilter, String orderBy, String search, String searchValue);
   int searchCount(String dateFilter, String orderBy, String search, String searchValue);

// 매거진 삭제
   void deleteMagazine(List<Integer> magazineId);

// 매거진 작성
   void insertMagazine(MagazinesDTO magazinesDTO);

// 작성한 매거진 가져오기
   MagazinesDTO selectMagazine(Long magazineId);

// 작성한 매거진 수정하기
   void updateMagazine(MagazinesDTO magazinesDTO);

// 작성한 매거진 이미지 목록
  List<MagazineImagesDTO> selectMagazineImage(Long magazineId);
}
