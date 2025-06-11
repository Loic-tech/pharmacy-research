package com.search.pharmacy.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

  @Bean
  public MinioClient getMinioClient() {
    return new MinioClient.Builder()
        .credentials("YMRxrDr4iuA3BgPe", "Qnkzj42WFpZVSgmdMJt1JSTWny47rcoh")
            .endpoint("http://localhost:9000")
        .build();
  }
}
