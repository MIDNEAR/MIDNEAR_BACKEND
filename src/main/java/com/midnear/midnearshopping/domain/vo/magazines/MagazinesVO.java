package com.midnear.midnearshopping.domain.vo.magazines;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagazinesVO {
    Long magazineId;
    String title;
    Date createdDate;
    Date modifiedDate;
    Long viewCount;
    String mainImage;
}
