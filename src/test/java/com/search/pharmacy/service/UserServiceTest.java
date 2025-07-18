package com.search.pharmacy.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.search.pharmacy.ws.model.CompleteUserDTO;
import com.search.pharmacy.ws.model.PartialUserDTO;
import com.search.pharmacy.ws.model.UserDTO;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.profiles.active=test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

  @Autowired private UserService sut;

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_users() {
    // when
    List<UserDTO> actual = sut.getUsers();

    // then
    assertThat(actual).isNotNull();
    assertThat(actual).hasSize(3);
    assertThat(actual.get(0).getFirstName()).isEqualTo("Admin");
    assertThat(actual.get(1).getFirstName()).isEqualTo("User");
    assertThat(actual.get(1).getEmail()).isEqualTo("user@user.com");
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_create_a_partial_new_user() {
    // given
    PartialUserDTO expected =
        PartialUserDTO.builder().email("lolo@email.com").password("74123698").build();

    // when
    UserDTO actual = sut.partialUserCreation(expected);

    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getEmail()).isEqualTo("lolo@email.com");
    assertThat(actual.getRoles().get(0).getName()).isEqualTo("ROLE_USER");
    assertThat(actual.getValid()).isFalse();
    assertThat(actual.getId()).isEqualTo(4L);
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_create_a_complete_user() {
    // given
    Long userId = 3L;
    CompleteUserDTO completeUserDTO = CompleteUserDTO.builder()
            .birthDate("15/05/1990")
            .firstName("test")
            .lastName("test")
            .build();
      String content = "Doliprane";
      MockMultipartFile mockMultipartFile =
              new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes());

    // when
      UserDTO userDTO = sut.completeUserCreation(userId, completeUserDTO, List.of(mockMultipartFile));

      // then
      assertThat(userDTO).isNotNull();
      assertThat(userDTO.getFirstName()).isEqualTo("test");
      assertThat(userDTO.getLastName()).isEqualTo("test");
      assertThat(userDTO.getUrls()).hasSize(1);
      assertThat(userDTO.getUrls().get(0)).isEqualTo("https://images.pharmadoc-ci.com/test-images/test.txt");
      assertThat(userDTO.getBirthDate()).isEqualTo("15/05/1990");
      assertThat(userDTO.getValid()).isFalse();
      assertThat(userDTO.getId()).isEqualTo(3L);
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_a_user_by_id() {

    // given
    Long userId = 1L;

    // when
    UserDTO actual = sut.getUser(userId);

    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getFirstName()).isEqualTo("Admin");
    assertThat(actual.getLastName()).isEqualTo("Admin");
    assertThat(actual.getEmail()).isEqualTo("<EMAIL>");
    assertThat(actual.getRoles().get(0).getName()).isEqualTo("ROLE_ADMIN");
    assertThat(actual.getId()).isEqualTo(1L);
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_update_a_user_by_id() {

    // Given
    Long userId = 1L;
    Map<String, Object> fields = new java.util.HashMap<>();
    fields.put("firstName", "test");

    // when
    UserDTO actual = sut.update(userId, fields);

    // then
    assertThat(actual).isNotNull();
    assertThat(actual.getFirstName()).isEqualTo("test");
    assertThat(actual.getLastName()).isEqualTo("Admin");
    assertThat(actual.getEmail()).isEqualTo("<EMAIL>");
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_delete_a_user() {

    // given
    Long userId = 2L;

    // when
    sut.delete(userId);
    List<UserDTO> actual = sut.getUsers();

    // then
    assertThat(actual).hasSize(2);
  }
}
