package com.midnear.midnearshopping.domain.vo.review;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Builder
public class ReviewsVO {
    Long reviewId;
    Date createdAt;
    Date modifiedDate;
    int rating;
    String review;
    String reviewStatus;
    Integer userId;
    Long orderProductId;
    String approveStatus;
}
