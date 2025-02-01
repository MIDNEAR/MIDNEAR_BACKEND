package com.midnear.midnearshopping.service.magazine;

import com.midnear.midnearshopping.domain.dto.magazines.MagazineResponseDto;
import com.midnear.midnearshopping.domain.dto.magazines.MagazineResponseListDto;
import com.midnear.midnearshopping.mapper.magazines.magazinesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMagazineService {
    private final magazinesMapper magazinesMapper;
    private static final int pageSize = 8;

    public List<MagazineResponseListDto> getMagazineLists(int pageNumber, String sort) {
        int offset = (pageNumber - 1) * pageSize;
        return magazinesMapper.getUserMagazineList(offset, pageSize, sort);
    }
    public MagazineResponseDto getMagazines(Long magazineId) {
        MagazineResponseDto dto = magazinesMapper.getMagazine(magazineId);
        List<String> images = magazinesMapper.getMagazineImages(magazineId);
        dto.setImageUrls(images);
        return dto;
    }
    public List<MagazineResponseListDto> searchMagazineLists(int pageNumber, String sort, String searchValue) {
        int offset = (pageNumber - 1) * pageSize;
        return magazinesMapper.magazineUserSearch(offset, pageSize, sort, searchValue);
    }
    @Transactional
    public void getMagazine(Long magazineId) {
       magazinesMapper.updateViewCount(magazineId);
    }

}
