package com.midnear.midnearshopping.service.order;

import com.midnear.midnearshopping.domain.dto.order.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderProductService {

// 주문내역 최신순 띄우기
   List<OrderDTO> selectAll(int pageNumber);

   int count();
}
