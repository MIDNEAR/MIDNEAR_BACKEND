package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    // 버킷에 다중 파일 업로드
    public List<FileDto> uploadFiles(String directory, List<MultipartFile> files) {
        List<FileDto> fileInfo = new ArrayList<>();

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                try {
                    // 마지막에 /가 있는지 확인
                    if (directory.endsWith("/")) {
                        directory = directory.substring(0, directory.length() - 1);
                    }
                    // 파일 key
                    String key = directory + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

                    // 파일을 S3에 업로드
                    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .build();

                    PutObjectResponse response = s3Client.putObject(
                            putObjectRequest,
                            RequestBody.fromInputStream(file.getInputStream(), file.getSize())
                    );

                    // 업로드 성공 후 FileDto 생성
                    FileDto fileDto = FileDto.builder()
                            .fileSize((int) file.getSize())
                            .fileUrl("https://" + bucket + ".s3." + region + ".amazonaws.com/" + key)
                            .extension(getFileExtension(file.getOriginalFilename()))
                            .build();

                    fileInfo.add(fileDto);

                } catch (S3Exception e) {
                    // S3 관련 오류
                    System.err.println("S3 error: " + e.awsErrorDetails().errorMessage());
                    e.printStackTrace();
                    throw new RuntimeException("파일 업로드에 실패했습니다", e);

                } catch (IOException e) {
                    // 파일 IO 오류
                    System.err.println("IO error: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("파일 업로드 중 IO 오류가 발생했습니다.", e);
                }
            }
        }

        return fileInfo;
    }

    // 확장자 추출 메서드
    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");

        if (index == -1) {
            return "";
        }

        return fileName.substring(index + 1);
    }
}
