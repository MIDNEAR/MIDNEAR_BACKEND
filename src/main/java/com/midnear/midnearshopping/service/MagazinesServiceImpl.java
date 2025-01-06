package com.midnear.midnearshopping.service;
import com.midnear.midnearshopping.domain.dto.FileDto;
import com.midnear.midnearshopping.domain.dto.magazines.MagazineImagesDTO;
import com.midnear.midnearshopping.domain.dto.magazines.MagazinesDTO;
import com.midnear.midnearshopping.domain.dto.magazines.MagazinesListDTO;
import com.midnear.midnearshopping.domain.vo.magazines.MagazineImagesVO;
import com.midnear.midnearshopping.domain.vo.magazines.MagazinesVO;
import com.midnear.midnearshopping.mapper.magazines.magazinesMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class MagazinesServiceImpl implements MagazinesService {

    private final magazinesMapper magazinesMapper;
    private static final int pageSize = 2;

    private final S3Service s3Service;

//  매거진 목록 전체/최신순 정렬
    @Override
    public List<MagazinesListDTO> selectMagazineList(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return magazinesMapper.selectMagazineList(offset, pageSize);
    }
    @Override
    public int count() {
        return magazinesMapper.count();
    }

//  매거진 목록 필터링 검색
    @Override
    public List<MagazinesListDTO> magazineSearch(int pageNumber, String dateFilter, String orderBy, String search, String searchValue) {
        int offset = (pageNumber - 1) * pageSize;
        return magazinesMapper.magazineSearch(offset,pageSize,dateFilter,orderBy,search,searchValue);
    }

    @Override
    public int searchCount(String dateFilter, String orderBy, String search, String searchValue) {
        return magazinesMapper.searchCount(dateFilter,orderBy,search,searchValue);
    }

//  매거진 목록 삭제
    @Override
    public void deleteMagazine(List<Integer> magazineId) {
        for(int i=0; i<magazineId.size(); i++){
           magazinesMapper.deleteMagazines(magazineId.get(i));
        }
    }

    //  매거진 작성
    @Transactional
    @Override
    public void insertMagazine(MagazinesDTO magazinesDTO) {

        //버킷에 저장
        List<FileDto> fileInfo;
        try {
            fileInfo = s3Service.uploadFiles("magazine", magazinesDTO.getFiles());
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }

        // 대표 이미지 설정: 업로드된 파일 중 첫 번째 파일의 URL을 mainImage로 설정
        String mainImageUrl = null;
        if (!fileInfo.isEmpty()) {
            mainImageUrl = fileInfo.get(0).getFileUrl(); // 첫 번째 파일의 URL을 대표 이미지로 사용
        }

        // dto vo 로 수정 후 insert
        MagazinesVO magazinesVO = MagazinesVO.builder()
                .title(magazinesDTO.getTitle())
                .content(magazinesDTO.getContent())
                .mainImage(mainImageUrl).build();

        magazinesMapper.insertMagazine(magazinesVO);


        //magazine_image 테이블에 이미지 정보(버킷링크) 저장
        try {
            for (FileDto file : fileInfo) {
                MagazineImagesVO imagesVo = MagazineImagesVO.builder()
                        .imageUrl(file.getFileUrl())
                        .fileSize(file.getFileSize())
                        .extension(file.getExtension())
                        .magazineId(magazinesVO.getMagazineId())
                        .build();
                magazinesMapper.uploadNoticeImages(imagesVo);
            }

        } catch (Exception e) {
            // S3에 이미 업로드된 파일 삭제
            for (FileDto file : fileInfo) {
                s3Service.deleteFile(file.getFileUrl());
            }
            e.printStackTrace();
            throw new RuntimeException("이미지 정보를 데이터베이스에 저장하는 중 오류가 발생했습니다.", e);
        }
    }

//  작성한 매거진 가져오기
    @Override
    public MagazinesDTO selectMagazine(Long magazineId) {
        return magazinesMapper.selectMagazine(magazineId);
    }

// 작성한 매거진 이미지 가져오기
    @Override
    public List<MagazineImagesDTO> selectMagazineImage(Long magazineId) {
        return magazinesMapper.selectMagazineImage(magazineId);
    }

//  작성한 매거진 수정하기
    @Override
    @Transactional
    public void updateMagazine(MagazinesDTO magazinesDTO) {

        // 기존 매거진 이미지 s3에서 삭제
        List<MagazineImagesDTO> magazineImages = magazinesMapper.selectMagazineImage(magazinesDTO.getMagazineId());
        if (magazineImages == null) {
            throw new RuntimeException("존재하지 않는 매거진입니다.");
        }
        for (int i = 0; i < magazineImages.size(); i++) {
                try {
                    s3Service.deleteFile(magazineImages.get(i).getImageUrl());
                } catch (Exception deleteException) {
                    log.error("이미지 삭제 실패: {}", magazineImages.get(i).getImageUrl(), deleteException);
                }
            }


        // 새 이미지 버킷에 저장
        List<FileDto> fileInfo;
        try {
            fileInfo = s3Service.uploadFiles("magazine", magazinesDTO.getFiles());
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }

        // 대표 이미지 설정: 업로드된 파일 중 첫 번째 파일의 URL을 mainImage로 설정
        String mainImageUrl = null;
        if (!fileInfo.isEmpty()) {
            mainImageUrl = fileInfo.get(0).getFileUrl(); // 첫 번째 파일의 URL을 대표 이미지로 사용
        }

        // dto vo 로 수정 후 insert
        MagazinesVO magazinesVO = MagazinesVO.builder()
                .title(magazinesDTO.getTitle())
                .content(magazinesDTO.getContent())
                .mainImage(mainImageUrl).build();

        magazinesMapper.updateMagazine(magazinesVO);


        //magazine_image 테이블에 이미지 정보(버킷링크) 저장
        try {
            for (FileDto file : fileInfo) {
                MagazineImagesVO imagesVo = MagazineImagesVO.builder()
                        .imageUrl(file.getFileUrl())
                        .fileSize(file.getFileSize())
                        .extension(file.getExtension())
                        .magazineId(magazinesVO.getMagazineId())
                        .build();
                magazinesMapper.uploadNoticeImages(imagesVo);
            }

        } catch (Exception e) {
            // S3에 이미 업로드된 파일 삭제
            for (FileDto file : fileInfo) {
                s3Service.deleteFile(file.getFileUrl());
            }
            e.printStackTrace();
            throw new RuntimeException("이미지 정보를 데이터베이스에 저장하는 중 오류가 발생했습니다.", e);
        }
    }

}
