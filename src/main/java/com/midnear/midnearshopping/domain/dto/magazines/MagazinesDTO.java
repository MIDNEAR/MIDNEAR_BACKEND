package com.midnear.midnearshopping.domain.dto.magazines;

import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import com.midnear.midnearshopping.domain.dto.notice.NoticeImagesDto;
import com.midnear.midnearshopping.domain.vo.magazines.MagazinesVO;
import com.midnear.midnearshopping.domain.vo.notice.NoticeImagesVo;
import com.midnear.midnearshopping.domain.vo.notice.NoticeVo;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MagazinesDTO {
        private Long magazineId;
        private String title;
        private String content;
        private String mainImage;
        private List<MultipartFile> files;
        private List<String> imageUrl;
    }



