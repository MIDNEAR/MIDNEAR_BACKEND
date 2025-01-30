package com.midnear.midnearshopping.domain.dto.storeImages;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreImagesDto {
    private String imageUrl;
    private String imageName;
    private MultipartFile file;
}
