package com.search.pharmacy.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

  @Bean
  public MinioClient getMinioClient() {
    return new MinioClient.Builder()
        .credentials("LVcaPzsOxiAMxpEQ", "PDyTgPlebuJyjrfsQYcarNw3qj6Rlr8F")
            .endpoint("http://localhost:9000")
        .build();
  }
}
