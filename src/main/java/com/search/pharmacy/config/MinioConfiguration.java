package com.search.pharmacy.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

  @Bean
  public MinioClient getMinioClient() {
    return new MinioClient.Builder()
        .credentials("PBIiMiLa0pfy4A3G", "n1cDeupaGxQ9zSZRnglYIdxMN1NBB6PJ")
            .endpoint("https://minio.pharmadoc-ci.com/")
        .build();
  }
}
