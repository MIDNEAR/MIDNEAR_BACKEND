package com.midnear.midnearshopping.domain.dto.coupon_point;

import lombok.*;

import java.sql.Date;


@Getter
@Setter
public class UserPointDto {
    Long amount;
    String reason;
    String productName;
    Date grantDate;
}
