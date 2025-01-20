package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmPurchaseDTO {
    Long orderNumber;
    Date buyConfirmDate;
    Date paymentDate;
    String orderName;
    String recipientName;
    String orderContact;
    String recipientContact;
    String postalCode;
    String address;
    String detailedAddress;
    String courier;
    Long invoiceNumber;
    String productName;
    String color;
    String size;
    int quantity;
    BigDecimal price;
    BigDecimal pointDiscount;
    BigDecimal couponDiscount;
    BigDecimal totalPaymentAmount;
    BigDecimal deliveryCharge;
    BigDecimal allPayment;
}
