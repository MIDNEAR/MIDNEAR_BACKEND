package com.midnear.midnearshopping.domain.vo.review;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Builder
public class ReviewImagesVO {
    Long reviewImageId;
    String reviewImageUrl;
    Long fileSize;
    String extension;
    Date creationDate;
    Long reviewId;
    String comment;
}
