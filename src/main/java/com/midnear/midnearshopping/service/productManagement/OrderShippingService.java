package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.OrderShippingDTO;
import net.nurigo.sdk.message.model.Count;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderShippingService {
// 주문상품 전체 , 최신순 정렬
   List<OrderShippingDTO> selectAll(int pageNumber);
   int count();
}
