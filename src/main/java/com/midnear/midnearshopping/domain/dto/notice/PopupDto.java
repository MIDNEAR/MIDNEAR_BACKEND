package com.midnear.midnearshopping.domain.dto.notice;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopupDto {
    private String imageUrl;
    private Long noticeId;
}
