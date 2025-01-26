package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ReturnDTO;
import com.midnear.midnearshopping.mapper.productManagement.ReturnMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements ReturnService {

    private final ReturnMapper returnMapper;

    private static final int pageSize = 2;

    // 전체, 최신순 정렬
    @Override
    public List<ReturnDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return returnMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return returnMapper.count();
    }
}
