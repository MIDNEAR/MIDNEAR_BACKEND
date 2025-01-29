package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.FileDto;
import com.midnear.midnearshopping.domain.dto.category.CreateCategoryDto;
import com.midnear.midnearshopping.domain.dto.storeImages.StoreImagesDto;
import com.midnear.midnearshopping.domain.vo.category.CategoryVo;
import com.midnear.midnearshopping.domain.vo.storeImages.StoreImagesVo;
import com.midnear.midnearshopping.mapper.Category.CategoriesMapper;
import com.midnear.midnearshopping.mapper.storeManagement.StoreImagesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreManagementService {
    private final StoreImagesMapper storeImagesMapper;
    private final CategoriesMapper categoriesMapper;
    private final S3Service s3Service;

    public StoreImagesDto getMainImage() {
        StoreImagesVo storeImagesVo = storeImagesMapper.findMainImage();
        String fileName = storeImagesVo.getImageUrl().substring(storeImagesVo.getImageUrl().lastIndexOf('_') + 1);
        return StoreImagesDto.builder()
                .imageUrl(storeImagesVo.getImageUrl())
                .imageName(fileName)
                .build();
    }

    @Transactional
    public void modifyMainImage(StoreImagesDto storeImagesDto) {
        List<FileDto> fileInfo = null;
        try {
            // 기존 이미지 삭제
            StoreImagesVo storeImagesVo = storeImagesMapper.findMainImage();
            if (storeImagesVo.getImageUrl() != null)
                s3Service.deleteFile(storeImagesVo.getImageUrl());

            // 새로운 파일 업로드
            fileInfo = s3Service.uploadFiles("main", Collections.singletonList(storeImagesDto.getFile()));

            // DB에 이미지 정보 변경
            storeImagesMapper.deleteImageById(storeImagesVo.getImageId());
            for (FileDto file : fileInfo) {
                StoreImagesVo newImagesVo = StoreImagesVo.builder()
                        .imageId(null)
                        .imageUrl(file.getFileUrl())
                        .fileSize(file.getFileSize())
                        .extension(file.getExtension())
                        .creationDate(null)
                        .type("main")
                        .build();
                storeImagesMapper.uploadImage(newImagesVo);
            }
        } catch (S3Exception s3Ex) {
            throw new RuntimeException("S3 처리 중 오류가 발생했습니다.", s3Ex);
        }  catch (Exception ex) {
            if (fileInfo != null) {
                for (FileDto file : fileInfo) {
                    s3Service.deleteFile(file.getFileUrl());
                }
            }
            throw new RuntimeException("DB 업데이트 중 오류가 발생했습니다", ex);
        }
    }

    public StoreImagesDto getLogoImage() {
        StoreImagesVo storeImagesVo = storeImagesMapper.findLogoImage();
        String fileName = storeImagesVo.getImageUrl().substring(storeImagesVo.getImageUrl().lastIndexOf('_') + 1);
        return StoreImagesDto.builder()
                .imageUrl(storeImagesVo.getImageUrl())
                .imageName(fileName)
                .build();
    }

    @Transactional
    public void modifyLogoImage(StoreImagesDto storeImagesDto) {
        List<FileDto> fileInfo = null;
        try {
            // 기존 이미지 삭제
            StoreImagesVo storeImagesVo = storeImagesMapper.findLogoImage();
            if (storeImagesVo.getImageUrl() != null)
                s3Service.deleteFile(storeImagesVo.getImageUrl());

            // 새로운 파일 업로드
            fileInfo = s3Service.uploadFiles("Logo", Collections.singletonList(storeImagesDto.getFile()));

            // DB에 이미지 정보 변경
            storeImagesMapper.deleteImageById(storeImagesVo.getImageId());
            for (FileDto file : fileInfo) {
                StoreImagesVo newImagesVo = StoreImagesVo.builder()
                        .imageId(null)
                        .imageUrl(file.getFileUrl())
                        .fileSize(file.getFileSize())
                        .extension(file.getExtension())
                        .creationDate(null)
                        .type("Logo")
                        .build();
                storeImagesMapper.uploadImage(newImagesVo);
            }
        } catch (S3Exception s3Ex) {
            throw new RuntimeException("S3 처리 중 오류가 발생했습니다.", s3Ex);
        }  catch (Exception ex) {
            if (fileInfo != null) {
                for (FileDto file : fileInfo) {
                    s3Service.deleteFile(file.getFileUrl());
                }
            }
            throw new RuntimeException("DB 업데이트 중 오류가 발생했습니다", ex);
        }
    }

    @Transactional
    public void createNewCategory(List<CreateCategoryDto> createCategoryDtoList) {
        for (CreateCategoryDto createCategoryDto : createCategoryDtoList) {
            CategoryVo categoryVo = CategoryVo.builder()
                    .parentCategoryId(createCategoryDto.getParentCategoryId())
                    .categoryName(createCategoryDto.getCategoryName())
                    .build();
            categoriesMapper.insertCategory(categoryVo);
        }
    }
}
