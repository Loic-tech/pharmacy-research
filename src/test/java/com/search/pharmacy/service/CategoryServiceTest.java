package com.search.pharmacy.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.search.pharmacy.ws.model.CategoryDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class CategoryServiceTest {

  @Autowired private CategoryService sut;
  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  @Sql(scripts = "classpath:service/test_category_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_categories() {
    List<CategoryDTO> actual = sut.getCategories();

    // Then
    assertThat(actual).hasSize(3);
    assertThat(actual.get(0).getName()).isEqualTo("Bebes");
    assertThat(actual.get(1).getName()).isEqualTo("Visage");
    assertThat(actual.get(2).getName()).isEqualTo("Maux de tete");
  }

  @Test
  @Sql(scripts = "classpath:service/test_category_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_create_a_new_category() {

    // Given
    CategoryDTO expected = CategoryDTO.builder().name("Soins Capillaires").build();

    // When
    CategoryDTO actual = sut.create(expected);

    // Then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("Soins Capillaires");
    assertThat(actual.getId()).isEqualTo(4);
  }

  @Test
  @Sql(scripts = "classpath:service/test_category_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_a_category_by_id() {

    // Given
    Long categoryId = 1L;

    // When
    CategoryDTO actual = sut.getCategory(categoryId);

    // Then
    assertThat(actual).isNotNull();
    assertThat(actual.getId()).isEqualTo(1);
    assertThat(actual.getName()).isEqualTo("Bebes");
  }

  @Test
  @Sql(scripts = "classpath:service/test_category_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_update_a_category_by_id() {

    // given
    Long categoryId = 1L;
    Map<String, Object> fields = new HashMap<>();
    fields.put("name", "Soins Capillaires");

    // when
    CategoryDTO actual = sut.update(categoryId, fields);

    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("Soins Capillaires");
  }

  @Test
  @Sql(scripts = "classpath:service/test_category_service.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_delete_a_category() {
    // given
    Long categoryId = 1L;

    // when
    sut.delete(categoryId);
    List<CategoryDTO> expected = sut.getCategories();

    // then
    assertThat(expected).hasSize(2);
  }
}
