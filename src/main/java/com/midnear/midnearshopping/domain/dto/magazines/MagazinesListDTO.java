package com.midnear.midnearshopping.domain.dto.magazines;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MagazinesListDTO {
    private Long magazineId;
    private String title;
    private String content;
    private Date createdDate;
    private Long viewCount;
}
