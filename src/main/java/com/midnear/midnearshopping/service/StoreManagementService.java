package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.FileDto;
import com.midnear.midnearshopping.domain.dto.Statistics.StatisticsDto;
import com.midnear.midnearshopping.domain.dto.category.CreateCategoryDto;
import com.midnear.midnearshopping.domain.dto.policies_info.PoliciesAndInfoDto;
import com.midnear.midnearshopping.domain.dto.storeImages.StoreImagesDto;
import com.midnear.midnearshopping.domain.vo.category.CategoryVo;
import com.midnear.midnearshopping.domain.vo.policies_info.PoliciesAndInfoVo;
import com.midnear.midnearshopping.domain.vo.storeImages.StoreImagesVo;
import com.midnear.midnearshopping.mapper.Category.CategoriesMapper;
import com.midnear.midnearshopping.mapper.policies_info.PoliciesAndInfoMapper;
import com.midnear.midnearshopping.mapper.storeManagement.StatisticsMapper;
import com.midnear.midnearshopping.mapper.storeManagement.StoreImagesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreManagementService {
    private final StoreImagesMapper storeImagesMapper;
    private final CategoriesMapper categoriesMapper;
    private final PoliciesAndInfoMapper policiesAndInfoMapper;
    private final StatisticsMapper statisticsMapper;
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

    @Transactional
    public void deleteCategory(List<Long> deleteList) {
        categoriesMapper.deleteCategories(deleteList);
    }

    public PoliciesAndInfoDto getPrivacyPolicy() {
        PoliciesAndInfoVo policiesAndInfoVo = policiesAndInfoMapper.getPrivacyPolicy();
        return PoliciesAndInfoDto.toEntity(policiesAndInfoVo);
    }

    public PoliciesAndInfoDto getTermsOfService() {
        PoliciesAndInfoVo policiesAndInfoVo = policiesAndInfoMapper.getTermsOfService();
        return PoliciesAndInfoDto.toEntity(policiesAndInfoVo);
    }

    public PoliciesAndInfoDto getBusinessInfo() {
        PoliciesAndInfoVo policiesAndInfoVo = policiesAndInfoMapper.getBusinessInfo();
        return PoliciesAndInfoDto.toEntity(policiesAndInfoVo);
    }

    public PoliciesAndInfoDto getDataUsage() {
        PoliciesAndInfoVo policiesAndInfoVo = policiesAndInfoMapper.getDataUsage();
        return PoliciesAndInfoDto.toEntity(policiesAndInfoVo);
    }

    @Transactional
    public void insertData(PoliciesAndInfoDto policiesAndInfoDto) {
        // 기존 데이터 삭제
        policiesAndInfoMapper.deleteData(policiesAndInfoDto.getType());

        // 새로운 데이터 삽입
        PoliciesAndInfoVo policiesAndInfoVo = PoliciesAndInfoVo.builder()
                .text(policiesAndInfoDto.getText())
                .type(policiesAndInfoDto.getType())
                .build();
        policiesAndInfoMapper.insertData(policiesAndInfoVo);
    }

    public List<StatisticsDto> getDailySales(Date startDate) {
        List<StatisticsDto> response = new ArrayList<>();

        LocalDate localStartDate = startDate.toLocalDate();

        for (int i = 0; i < 7; i++) {
            LocalDate targetDate = localStartDate.plusDays(i);
            Date date = Date.valueOf(targetDate);

            // 결제 금액
            Long paymentAmount = statisticsMapper.getDailySales(date);
            if (paymentAmount == null) paymentAmount = 0L;

            // 환불 금액
            Long refundAmount = statisticsMapper.getRefundedTotalPrice(date);
            if (refundAmount == null) refundAmount = 0L;

            StatisticsDto statisticsDto = StatisticsDto.builder()
                    .date(date)
                    .refundAmount(refundAmount)
                    .paymentAmount(paymentAmount)
                    .build();

            response.add(statisticsDto);
        }

        return response;
    }

    public List<StatisticsDto> getWeeklySales(Date startDate) {
        List<StatisticsDto> response = new ArrayList<>();

        LocalDate localStartDate = startDate.toLocalDate();

        // 5주 동안 반복
        for (int i = 0; i < 5; i++) {
            // 주 시작일과 종료일 계산
            LocalDate weekStartDate = localStartDate.withDayOfMonth(i * 7 + 1);
            LocalDate weekEndDate = localStartDate.withDayOfMonth(Math.min((i + 1) * 7, localStartDate.lengthOfMonth()));

            // yyyy-MM-dd 형식의 날짜로 변환
            String start = weekStartDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String end = weekEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 결제 금액
            Long paymentAmount = statisticsMapper.getWeeklySales(start, end);
            if (paymentAmount == null) paymentAmount = 0L; // null인 경우 0으로 반환

            // 환불 금액
            Long refundAmount = statisticsMapper.getWeeklyRefundedTotalPrice(start, end);
            if (refundAmount == null) refundAmount = 0L; // null인 경우 0으로 반환

            // 주 시작일 기준으로 반환
            Date date = Date.valueOf(weekStartDate);

            StatisticsDto statisticsDto = StatisticsDto.builder()
                    .date(date)
                    .paymentAmount(paymentAmount)
                    .refundAmount(refundAmount)
                    .build();

            response.add(statisticsDto);
        }

        return response;
    }

    public List<StatisticsDto> getMonthlySales(Date startDate) {
        List<StatisticsDto> response = new ArrayList<>();

        LocalDate localStartDate = startDate.toLocalDate();

        // 4개월
        for (int i = 0; i < 4; i++) {
            String yearMonth = localStartDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

            // 결제 금액
            Long paymentAmount = statisticsMapper.getMonthlySales(yearMonth);
            if (paymentAmount == null) paymentAmount = 0L; // null인 경우 0으로 반환

            // 환불 금액
            Long refundAmount = statisticsMapper.getMonthlyRefundedTotalPrice(yearMonth);
            if (refundAmount == null) refundAmount = 0L; // null인 경우 0으로 반환

            Date date = Date.valueOf(localStartDate.withDayOfMonth(1));

            StatisticsDto statisticsDto = StatisticsDto.builder()
                    .date(date)
                    .paymentAmount(paymentAmount)
                    .refundAmount(refundAmount)
                    .build();

            response.add(statisticsDto);

            // 다음 달
            localStartDate = localStartDate.plusMonths(1);
        }

        return response;
    }
}
