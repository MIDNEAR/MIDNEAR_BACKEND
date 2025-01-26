package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeDTO;
import com.midnear.midnearshopping.mapper.productManagement.ExchangeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeMapper exchangeMapper;
    private static final int pageSize = 2;


    @Override
    public List<ExchangeDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return exchangeMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return exchangeMapper.count();
    }
}
