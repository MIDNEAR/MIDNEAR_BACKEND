package com.midnear.midnearshopping.domain.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    Long reviewId;
    String comment;
}
