package com.search.pharmacy.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonpatch.JsonPatch;
import com.search.pharmacy.ws.model.MedicineDTO;
import io.restassured.http.ContentType;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.profiles.active=test")
@WithMockUser
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MedicineControllerIT {

  @LocalServerPort private int port;
  @Autowired ObjectMapper objectMapper;

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_medicines() {
    // given
    String uri = "/medicine";

    // when
    given()
        .get(buildURL(uri))
        .prettyPeek()
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("size()", is(3));
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_create_medicine() {
    // given
    String uri = "/medicine";

    MedicineDTO medicineDTO =
        MedicineDTO.builder().name("propranolol").description("Maux de tête").build();

    given()
        .body(medicineDTO)
        .contentType(ContentType.JSON)
        .post(buildURL(uri))
        .prettyPeek()
        .then()
        //
        .contentType(ContentType.JSON)
        .body("id", is(4))
        .body("name", is("propranolol"))
        .body("description", is("Maux de tête"));
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
  }

  public final String buildURL(String uri) {
    return "http://localhost:" + port + "/api" + uri;
  }
}
