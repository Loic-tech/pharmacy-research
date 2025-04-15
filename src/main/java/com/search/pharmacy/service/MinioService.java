package com.search.pharmacy.service;

import com.search.pharmacy.ws.mapper.FileMapper;
import com.search.pharmacy.ws.model.FileDTO;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {

  private final MinioClient minioClient;
  Map<String, String> reqParams = new HashMap<String, String>();

  public String generatePresignedUrl(String name) throws Exception {

    reqParams.put("response-content-type", "application/json");

    String url =  minioClient.getPresignedObjectUrl(
        GetPresignedObjectUrlArgs.builder()
            .method(Method.GET)
            .bucket("choupi")
            .object(name)
            .expiry(2, TimeUnit.HOURS)
            .extraQueryParams(reqParams)
            .build());

    return url;
  }

  public void uploadFiles(MultipartFile file) throws Exception {
    minioClient.putObject(
            PutObjectArgs.builder().bucket("choupi").object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
  }
}
