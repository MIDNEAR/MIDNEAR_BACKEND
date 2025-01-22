package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.CancelProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CancelProductService {

//  전체,최신순 취소요청 띄우기
    List<CancelProductDTO> selectAll(int pageNumber);

    int count();
}
