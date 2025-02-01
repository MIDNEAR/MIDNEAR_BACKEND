package com.midnear.midnearshopping.domain.dto.magazines;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class MagazineResponseDto {
    Long magazineId;
    String title;
    String content;
    Date createdDate;
    List<String> imageUrls;

}
