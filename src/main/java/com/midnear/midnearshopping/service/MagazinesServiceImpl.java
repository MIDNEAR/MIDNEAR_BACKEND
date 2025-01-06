package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.magazines.MagazinesListDTO;
import com.midnear.midnearshopping.mapper.disruptive.DisruptiveMapper;
import com.midnear.midnearshopping.mapper.magazines.magazinesMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class MagazinesServiceImpl implements MagazinesService {

    private final magazinesMapper magazinesMapper;
    private static final int pageSize = 2;

//  매거진 목록 전체/최신순 정렬
    @Override
    public List<MagazinesListDTO> selectMagazineList(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return magazinesMapper.selectMagazineList(offset, pageSize);
    }
    @Override
    public int count() {
        return magazinesMapper.count();
    }


}
