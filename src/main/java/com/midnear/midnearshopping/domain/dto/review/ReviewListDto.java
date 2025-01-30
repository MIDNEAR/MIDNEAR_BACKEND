package com.midnear.midnearshopping.domain.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReviewListDto {
    Long reviewId;
    String id;
    Date created;
    int rating;
    String color;
    String size;
    List<String> imagesPerReview;
    String content;
    int isReply;
    String comment;
}
