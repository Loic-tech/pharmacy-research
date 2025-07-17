package com.search.pharmacy.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import com.search.pharmacy.config.SecurityConfig;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties =
        "spring.profiles.active=test, "
            + "\"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration\"")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(SecurityConfig.class)
public class MedicineControllerIT {

  @LocalServerPort private int port;

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_medicines() {
    // given
    String uri = "/medicine";

    // when
    given()
        .auth()
        .basic("Admin", "<PASSWORD>") // ou .preemptive().basic()
        .when()
        .get(buildURL(uri))
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("size()", is(2));
  }

  /*  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  @WithMockUser(roles = "ADMIN")
  public void should_create_medicine() throws IOException {
    // given
    String uri = "/medicine";

    String content = "Doliprane";
    MockMultipartFile mockMultipartFile =
        new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes());

    Map<String, Object> fields = new HashMap<>();
    fields.put("name", "propranolol");
    fields.put("smallDescription", "Maux de tête");
    fields.put("completeDescription", "my complete description");
    fields.put("newPrice", 100.0);
    fields.put("oldPrice", 120.0);
    fields.put("quantity", 50);
    fields.put("usingAdvice", "my advice");
    fields.put("composition", "my composition");
    fields.put("idCategory", 1);

    given()
        .multiPart("medicineDTO", fields)
        .multiPart("file", "file.txt", mockMultipartFile.getInputStream())
        .post(buildURL(uri))
        .prettyPeek()
        .then()
        //
        .statusCode(HttpStatus.OK.value())
        .contentType(ContentType.JSON)
        .body("id", is(4))
        .body("name", is("propranolol"))
        .body("smallDescription", is("Maux de tête"))
        .body("completeDescription", is("my complete description"))
        .body("newPrice", is(100.0));
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_get_medicine_by_id() {

    given()
        .contentType(ContentType.JSON)
        .get(buildURL("/medicine/2"))
        .prettyPeek()
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("id", is(2))
        .body("name", is("Artrine"))
        .body("description", is("Malaria"));
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_update_medicine_by_id() throws IOException {
    // given
    JsonPatch jsonPatch =
        objectMapper.convertValue(
            JsonLoader.fromResource("/service/update-medicine.json"), JsonPatch.class);

    given()
        .body(objectMapper.writeValueAsString(jsonPatch))
        .contentType(ContentType.JSON)
        .patch(buildURL("/medicine/2"))
        .prettyPeek()
        .then()
        // then
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("id", is(2))
        .body("name", is("FERVEX"))
        .body("description", is("pains"));
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_delete_medicine_by_id() {
    given().delete(buildURL("/medicine/1")).then().statusCode(200);
  }*/

  public final String buildURL(String uri) {
    return "http://localhost:" + port + "/api" + uri;
  }
}
