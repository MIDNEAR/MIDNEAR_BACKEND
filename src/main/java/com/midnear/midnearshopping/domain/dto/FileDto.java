package com.midnear.midnearshopping.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDto {
    private String fileUrl;
    private Long fileSize;
    private String extension;
}
