package com.search.pharmacy.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

  @Bean
  public MinioClient getMinioClient() {
    return new MinioClient.Builder()
        .endpoint("http://31.97.153.208:9000")
        .credentials("aF1DNQQN6dNWl9Gi", "uNPNFltyc35dJMTVvnWock9U7NYlWGpm")
        .build();
  }
}
