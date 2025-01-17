package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.OrderDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderProductService {

// 주문내역 최신순 띄우기
   List<OrderDTO> selectAll(int pageNumber);

   int count();

// 주문내역 필터링 조회
   List<OrderDTO> filterSearch(ParamDTO orderParamDTO);

   int filterCount(ParamDTO orderParamDTO);
}
