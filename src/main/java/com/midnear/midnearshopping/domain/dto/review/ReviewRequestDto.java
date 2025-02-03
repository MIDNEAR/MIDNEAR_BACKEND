package com.midnear.midnearshopping.domain.dto.review;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReviewRequestDto {

    int rating;
    String review;
    Long userId;
    Long orderProductId;
    private List<MultipartFile> files;
}
