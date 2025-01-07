package com.midnear.midnearshopping.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDto {
    private String fileUrl;
    private long fileSize;
    private String extension;
}
