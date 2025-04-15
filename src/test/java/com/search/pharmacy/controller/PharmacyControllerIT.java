package com.search.pharmacy.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonpatch.JsonPatch;
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
public class PharmacyControllerIT {

  @LocalServerPort private int port;
  @Autowired ObjectMapper objectMapper;

  @Test
  @Sql(scripts = "classpath:service/test_it_pharmacy_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_pharmacies() {
    // given
    String uri = "/pharmacies";

    // when
    given()
        .contentType(ContentType.JSON)
        .get(buildURL(uri))
        .prettyPeek()
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("size()", is(3))
        .body("[0].id", is(1))
        .body("[0].name", is("Pharmacie De Lafayette"))
        .body("[0].address", is("10 Bd de Sébastopol, 75004 Paris"))
        .body("[1].id", is(2))
        .body("[1].name", is("La Grande Pharmacie du 15"))
        .body("[1].address", is("119 Rue Saint-Charles 75015 Paris France"))
        .body("[2].id", is(3))
        .body("[2].name", is("Pharmacie EIFFEL COMMERCE - Paris 15"))
        .body("[2].address", is("31 Rue de Rivoli, 75004 Paris, France"));
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_pharmacy_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_create_pharmacy() {
    // given
    String uri = "/pharmacies";

    PharmacyDTO pharmacyDTO =
        PharmacyDTO.builder()
            .name("Pharmacy St James")
            .address("Chelsea")
            .contact("123456789")
            .longitude(14.0)
            .latitude(17.0)
            .build();

    given()
        .body(pharmacyDTO)
        .contentType(ContentType.JSON)
        .post(buildURL(uri))
        .prettyPeek()
        .then()
        //
        .contentType(ContentType.JSON)
        .body("id", is(4))
        .body("name", is("Pharmacy St James"))
        .body("address", is("Chelsea"))
        .body("longitude", is(14.0F))
        .body("latitude", is(17.0F));
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_pharmacy_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_get_pharmacy_by_id() {
    given()
        .contentType(ContentType.JSON)
        .get(buildURL("/pharmacies/2"))
        .prettyPeek()
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("id", is(2))
        .body("name", is("La Grande Pharmacie du 15"))
        .body("address", is("119 Rue Saint-Charles 75015 Paris France"));
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_pharmacy_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_update_pharmacy() throws IOException {
    // given
    JsonPatch jsonPatch =
        objectMapper.convertValue(
            JsonLoader.fromResource("/service/update-pharmacy.json"), JsonPatch.class);

    given()
        .body(objectMapper.writeValueAsString(jsonPatch))
        .contentType(ContentType.JSON)
        .patch(buildURL("/pharmacies/2"))
        .prettyPeek()
        .then()
        // then
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("id", is(2))
        .body("name", is("Pharmacie des jardins"))
        .body("contact", is("01 23 45 67 89"))
        .body("latitude", is(48.84447F));
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_pharmacy_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_delete_pharmacy() {
    given().delete(buildURL("/pharmacies/1")).then().statusCode(200);
  }

  public final String buildURL(String uri) {
    return "http://localhost:" + port + "/api" + uri;
  }
}
