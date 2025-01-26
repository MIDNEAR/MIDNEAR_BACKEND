package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExchangeService {

//  최신순 필터링
    List<ExchangeDTO> selectAll(int pageSize);

    int count();
}
