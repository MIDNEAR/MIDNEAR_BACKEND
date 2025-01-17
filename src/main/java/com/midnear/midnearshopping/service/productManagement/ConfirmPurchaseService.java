package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ConfirmPurchaseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConfirmPurchaseService {
    // 주문내역 최신순 띄우기
    List<ConfirmPurchaseDTO> selectAll(int pageNumber);

    int count();
}
