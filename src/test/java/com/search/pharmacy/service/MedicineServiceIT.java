package com.search.pharmacy.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.ws.model.MedicineDTO;
import com.search.pharmacy.ws.model.MedicineDetailDTO;
import com.search.pharmacy.ws.model.MedicineListDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
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
  @Autowired private MedicineRepository medicineRepository;

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_medicines() {
    // given
    int page = 0;
    int size = 10;

    // When
    Page<MedicineListDTO> actual = sut.searchMedicines(null, null, page, size);

    // Then
    assertThat(actual).hasSize(2);
    assertThat(actual.getContent().get(0).getName()).isEqualTo("Doliprane");
    assertThat(actual.getContent().get(1).getName()).isEqualTo("Fervex");
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
          scripts = "classpath:service/dropTestData.sql",
          executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_medicines_by_category() {
    // given
    Long categoryId = 1L;
    int page = 0;
    int size = 10;

    // When
    Page<MedicineListDTO> actual = sut.searchMedicines(null, categoryId, page, size);

    // Then
    assertThat(actual).hasSize(1);
    assertThat(actual.getContent().get(0).getName()).isEqualTo("Doliprane");
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
          scripts = "classpath:service/dropTestData.sql",
          executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_medicines_by_name() {
    // given
    String name = "doliprane";
    int page = 0;
    int size = 10;

    // When
    Page<MedicineListDTO> actual = sut.searchMedicines(name, null, page, size);

    // Then
    assertThat(actual).hasSize(1);
    assertThat(actual.getContent().get(0).getName()).isEqualTo("Doliprane");
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_create_medicine() throws Exception {
    // Given
    String content = "Doliprane";
    MockMultipartFile mockMultipartFile =
        new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes());
    MedicineDTO expected =
        MedicineDTO.builder()
            .name("Doliprane")
            .smallDescription("smallDescription")
            .completeDescription("completeDescription")
            .composition("composition")
            .oldPrice(10.60)
            .newPrice(10.60)
            .quantity(54)
            .usingAdvice("advice")
            .idCategory(1L)
            .build();

    // When
    MedicineDTO actual = sut.create(expected, mockMultipartFile);

    // Then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("Doliprane");
    assertThat(actual.getSmallDescription()).isEqualTo("smallDescription");
    assertThat(actual.getCompleteDescription()).isEqualTo("completeDescription");
    assertThat(actual.getComposition()).isEqualTo("composition");
    assertThat(actual.getOldPrice()).isEqualTo(10.60);
    assertThat(actual.getNewPrice()).isEqualTo(10.60);
    assertThat(actual.getQuantity()).isEqualTo(54);
    assertThat(actual.getIdCategory()).isEqualTo(1L);
    assertThat(actual.getUrl()).isEqualTo("https://images.pharmadoc-ci.com/test-images/test.txt");
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_medicine_by_id() {

    // When
    MedicineDetailDTO actual = sut.getMedicine(2L);

    // Then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("Fervex");
    assertThat(actual.getSmallDescription()).isEqualTo("ma petite description 2");
    assertThat(actual.getUrl()).isEqualTo("http://localhost:9000/test-images/image-1.png");
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_update_medicine() {
    // Given
    Long id = 2L;
    Map<String, Object> fields = new HashMap<>();
    fields.put("name", "Doliprane");
    fields.put("composition", "the new composition");

    // When
    MedicineDetailDTO actual = sut.update(id, fields, null);

    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("Doliprane");
    assertThat(actual.getComposition()).isEqualTo("the new composition");
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_medicine_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_delete_medicine() {
    // Given
    Long medicineId = 2L;

    // When
    sut.delete(medicineId);

    // Then
    List<Medicine> actual = medicineRepository.findAll();
    assertThat(actual).hasSize(1);
  }
}
