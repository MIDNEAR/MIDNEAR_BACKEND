package com.midnear.midnearshopping.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDto {
    private String fileUrl;
    private int fileSize;
    private String extension;
}
