package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.magazines.MagazinesListDTO;
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
}
