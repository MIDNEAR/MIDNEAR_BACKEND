package com.midnear.midnearshopping.domain.dto.order;

import java.math.BigDecimal;
import java.util.Date;

public class UserOrderProductsDto {
    Long orderProductId;
    String size;
    int quantity;
    String orderStatus;
    BigDecimal couponDiscount;
    String claimStatus;
    Date buyConfirmDate;
    BigDecimal pointDiscount;
    Long productId;
    Long orderId;
}
