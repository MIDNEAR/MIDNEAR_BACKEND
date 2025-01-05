package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveDTO;
import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveListDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface DisruptiveService {
//  사용자 아이디 검색
    List<String> searchId(String id);

//  판매방해고객 등록
    void insertDisruptive(disruptiveDTO disruptiveDTO);

//  판매방해 등록 전체, 최신순 조회
    List<disruptiveListDTO> SelectDisruptlist(int pageNumber);
    int count();

//  검색
    List<disruptiveListDTO> disruptiveSearchInquiries(int pageNumber ,String dateFilter, String orderBy, String search, String searchValue);
    int searchCount(String dateFilter, String orderBy, String search, String searchValue);
}
