package com.search.pharmacy.controller;


import com.search.pharmacy.config.SecurityConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties =
        "spring.profiles.active=test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(SecurityConfig.class)
public class MedicineControllerIT {

  @LocalServerPort private int port;

  public final String buildURL(String uri) {
    return "http://localhost:" + port + "/api" + uri;
  }
}
