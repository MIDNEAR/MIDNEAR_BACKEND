package com.midnear.midnearshopping.domain.dto.magazines;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagazinesDTO {
        Long magazineId;
        String title;
        String content;
        Date createdDate;
        Long viewCount;
        String mainImage;
    }

