package com.midnear.midnearshopping.service.productManagement;
import com.midnear.midnearshopping.domain.dto.productManagement.CancelProductDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
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

//  필터링 검색
    @Override
    public List<CancelProductDTO> filterSearch(ParamDTO paramDTO) {
        int offset = (paramDTO.getPageNumber()- 1) * pageSize;
        paramDTO.setOffset(offset);
        paramDTO.setPageSize(pageSize);
        return cancelProductMapper.filterSearch(paramDTO);
    }

    @Override
    public int filterCount(ParamDTO paramDTO) {
        return cancelProductMapper.filterCount(paramDTO);
    }

//  선택내역 취소 승인처리
    @Override
    public void confirmCancel(List<Long> canceledProductId) {
        cancelProductMapper.confirmCancel(canceledProductId);
    }

    @Override
    public void denayCancel(List<Long> canceledProductId) {
        cancelProductMapper.denayCancel(canceledProductId);
    }
}
