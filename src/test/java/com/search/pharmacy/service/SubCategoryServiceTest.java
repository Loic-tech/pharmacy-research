package com.search.pharmacy.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.search.pharmacy.ws.model.SubCategoryDTO;
import java.util.List;
import java.util.Map;
import org.junit.Test;
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
public class SubCategoryServiceTest {

  @Autowired private SubCategoryService sut;

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_subcategories() {
    List<SubCategoryDTO> actual = sut.getSubCategories();

    // Then
    assertThat(actual).hasSize(3);
    assertThat(actual.get(0).getName()).isEqualTo("Laits du corps");
    assertThat(actual.get(1).getName()).isEqualTo("Huiles");
    assertThat(actual.get(2).getName()).isEqualTo("Douleurs");
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_create_a_new_sub_category() {

    // given
    SubCategoryDTO expected =
        SubCategoryDTO.builder().name("Soins Capillaires").idCategory(1L).build();

    // when
    SubCategoryDTO actual = sut.create(expected);

    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("Soins Capillaires");
    assertThat(actual.getIdCategory()).isEqualTo(1L);
    assertThat(actual.getId()).isEqualTo(4);
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_a_sub_category_by_id() {
    // given
    Long subCategoryId = 1L;

    // when
    SubCategoryDTO expected = sut.getSubCategory(subCategoryId);

    // then
    assertThat(expected).isNotNull();
    assertThat(expected.getId()).isEqualTo(1L);
    assertThat(expected.getName()).isEqualTo("Laits du corps");
    assertThat(expected.getIdCategory()).isEqualTo(1L);
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_update_a_sub_category_by_id() {

    // given
    Long subCategoryId = 1L;
    Map<String, Object> fields = new java.util.HashMap<>();
    fields.put("name", "test sub category");

    // when
    SubCategoryDTO expected = sut.update(subCategoryId, fields);

    // then
    assertThat(expected).isNotNull();
    assertThat(expected.getName()).isEqualTo("test sub category");
    assertThat(expected.getId()).isEqualTo(1L);
    assertThat(expected.getIdCategory()).isEqualTo(1L);
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_delete_a_sub_category() {

    // given
    Long subCategoryId = 1L;

    // when
    sut.delete(subCategoryId);
    List<SubCategoryDTO> expected = sut.getSubCategories();

    // then
    assertThat(expected).hasSize(2);
  }
}
