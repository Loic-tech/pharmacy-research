package com.search.pharmacy.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {

  private final MinioClient minioClient;

  public String uploadFiles(MultipartFile file) throws Exception {
    minioClient.putObject(
            PutObjectArgs.builder().bucket("test-images").object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
    
    return getPreSignedUrl(file.getOriginalFilename());
  }

  public void deleteFile(String filename) throws Exception {
    minioClient.removeObject(
            RemoveObjectArgs
                    .builder()
                    .bucket("test-images")
                    .object(filename)
                    .build()
    );
  }

  private String getPreSignedUrl(String filename) {
    return "https://images.pharmadoc-ci.com/test-images/".concat(filename);
  }
}
