package com.search.pharmacy.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.search.pharmacy.ws.model.MedicineDTO;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.profiles.active=test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MedicineServiceIT {

  @Autowired private MedicineService sut;
  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_medicines() {
    // When
    List<MedicineDTO> actual = sut.getMedicines();

    // Then
    assertThat(actual).hasSize(3);
  }

  @Test
  public void should_create_medicine() {
    // Given
    MedicineDTO expected = MedicineDTO.builder().name("Fervex").description("douleurs").build();

    // When
    MedicineDTO actual = sut.create(expected);

    // Then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("Fervex");
    assertThat(actual.getDescription()).isEqualTo("douleurs");
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_medicine_by_id() {

    // When
    MedicineDTO actual = sut.getMedicine(2L);

    // Then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("Artrine");
    assertThat(actual.getDescription()).isEqualTo("Malaria");
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_update_medicine() {
    // Given
    MedicineDTO patchMedicineDTO =
        MedicineDTO.builder().name("Fervex").description("douleurs").build();

    // When
    MedicineDTO actual = sut.update(2L, patchMedicineDTO);

    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("Fervex");
    assertThat(actual.getDescription()).isEqualTo("douleurs");
  }
}
