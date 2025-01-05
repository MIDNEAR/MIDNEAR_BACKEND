package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveDTO;
import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveListDTO;
import com.midnear.midnearshopping.mapper.disruptive.DisruptiveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisruptiveServiceImpl implements DisruptiveService {

    private final DisruptiveMapper disruptiveMapper;
    private static final int pageSize = 2;

//  아이디 검색
    @Override
    public List<String> searchId(String id) {
        return disruptiveMapper.searchId(id);
    }

//  판매 방해고객 등록
    @Override
    public void insertDisruptive(disruptiveDTO disruptiveDTO) {
        disruptiveMapper.insertDisruptive(disruptiveDTO);
    }

//  판매 방해고객 전체/최신순 정렬
    @Override
    public List<disruptiveListDTO> SelectDisruptlist(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return disruptiveMapper.SelectDisruptlist(offset, pageSize);
    }
    @Override
    public int count() {
        return disruptiveMapper.count();
    }

//  검색
    @Override
    public List<disruptiveListDTO> disruptiveSearchInquiries(int pageNumber, String dateFilter, String orderBy, String search, String searchValue) {
        int offset = (pageNumber - 1) * pageSize;
        return disruptiveMapper.disruptiveSearchInquiries(offset,pageSize,dateFilter,orderBy,search,searchValue);
    }
    @Override
    public int searchCount(String dateFilter, String orderBy, String search, String searchValue) {
        return disruptiveMapper.searchCount(dateFilter,orderBy,search,searchValue);
    }

    @Override
    @Transactional
    public void deleteDisrupt(List<Integer> disruptiveCustomerId) {
        for(int i=0; i<disruptiveCustomerId.size(); i++){
            disruptiveMapper.deleteDisrupt(disruptiveCustomerId.get(i));
        }
    }

}
