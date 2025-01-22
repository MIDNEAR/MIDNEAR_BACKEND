package com.midnear.midnearshopping.service.productManagement;
import com.midnear.midnearshopping.domain.dto.productManagement.CancelProductDTO;
import com.midnear.midnearshopping.mapper.productManagement.CancelProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CancelProductServiceImpl implements CancelProductService {

    private final CancelProductMapper cancelProductMapper;
    private static final int pageSize = 2;

//  전체, 최신순 취소요청 띄우기

    @Override
    public List<CancelProductDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return cancelProductMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return cancelProductMapper.count();
    }
}
