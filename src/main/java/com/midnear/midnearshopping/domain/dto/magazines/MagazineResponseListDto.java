package com.midnear.midnearshopping.domain.dto.magazines;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class MagazineResponseListDto {
    Long magazineId;
    String title;
    Date createdDate;
    String mainImage;
}
