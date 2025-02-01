package com.midnear.midnearshopping.mapper.order;

import com.midnear.midnearshopping.domain.dto.payment.PaymentDbDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    void updatePayment(PaymentDbDTO paymentDbDTO);
}
