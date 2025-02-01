package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.sql.Struct;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    String invoiceNumber;
    String productName;
    String color;
    String size;
    int quantity;
    BigDecimal productPrice;
    BigDecimal pointDiscount;
    BigDecimal couponDiscount;
    BigDecimal deliveryCharge;
    BigDecimal allPayment;
}
