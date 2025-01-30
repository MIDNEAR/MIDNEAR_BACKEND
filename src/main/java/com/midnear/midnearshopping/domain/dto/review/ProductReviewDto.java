package com.midnear.midnearshopping.domain.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductReviewDto {
    List<String> allReviewImages;
    int imageReviewCount;
    int reviewCount;
    List<ReviewListDto> reviewList;
}
