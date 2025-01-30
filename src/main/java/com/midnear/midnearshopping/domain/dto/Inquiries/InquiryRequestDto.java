package com.midnear.midnearshopping.domain.dto.Inquiries;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class InquiryRequestDto {
    private String title;
    private String content;
    private Long isPrivate;
    private Long userId;
}
