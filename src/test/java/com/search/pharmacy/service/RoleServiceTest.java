package com.search.pharmacy.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.search.pharmacy.ws.model.RoleDTO;
import java.util.List;
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
public class RoleServiceTest {

  @Autowired private RoleService sut;

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_roles() {

    // when
    List<RoleDTO> actual = sut.getRoles();

    // then
    assertThat(actual).hasSize(2);
    assertThat(actual.get(0).getName()).isEqualTo("ROLE_ADMIN");
    assertThat(actual.get(1).getName()).isEqualTo("ROLE_USER");
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_create_a_new_role() {

    // given
    RoleDTO expected = RoleDTO.builder().name("ROLE_TEST").build();

    // when
    RoleDTO actual = sut.createRole(expected);

    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getName()).isEqualTo("ROLE_TEST");
    assertThat(actual.getId()).isEqualTo(3L);
  }
}
