package com.midnear.midnearshopping.domain.dto.magazines;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MagazineImagesDTO {
    private String imageUrl;
    private Long fileSize;
    private String extension;
    private Date creationDate;
    private Long magazineId;
}
