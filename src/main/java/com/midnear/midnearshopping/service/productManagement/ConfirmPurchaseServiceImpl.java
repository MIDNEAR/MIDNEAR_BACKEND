package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ConfirmPurchaseDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import com.midnear.midnearshopping.mapper.productManagement.ConfirmPurchaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmPurchaseServiceImpl implements ConfirmPurchaseService {

    private final ConfirmPurchaseMapper confirmPurchaseMapper;

    private static final int pageSize = 2;

//  전체 최신순 조회
    @Override
    public List<ConfirmPurchaseDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return confirmPurchaseMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return confirmPurchaseMapper.count();
    }

//  필터링 검색
    @Override
    public List<ConfirmPurchaseDTO> filterSearch(ParamDTO paramDTO) {
        int offset = (paramDTO.getPageNumber()- 1) * pageSize;
        paramDTO.setOffset(offset);
        paramDTO.setPageSize(pageSize);
        return confirmPurchaseMapper.filterSearch(paramDTO);
    }

    @Override
    public int filterCount(ParamDTO paramDTO) {
        return confirmPurchaseMapper.filterCount(paramDTO);
    }
}
