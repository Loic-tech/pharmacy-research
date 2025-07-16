package com.search.pharmacy.service;

import com.search.pharmacy.ws.model.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.profiles.active=test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService sut;


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
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getFirstName()).isEqualTo("Admin");
        assertThat(actual.get(1).getFirstName()).isEqualTo("User");
        assertThat(actual.get(1).getEmail()).isEqualTo("user@user.com");
    }

    @Test
    @Sql(scripts = "classpath:service/datasets.sql")
    @Sql(
            scripts = "classpath:service/dropTestData.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void should_create_a_new_user() {
        // given
        UserDTO expected = UserDTO.builder().firstName("test").lastName("test").password("123456789").email("<EMAIL>").build();

        // when
        UserDTO actual = sut.create(expected);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getFirstName()).isEqualTo("test");
        assertThat(actual.getLastName()).isEqualTo("test");
        assertThat(actual.getEmail()).isEqualTo("<EMAIL>");
        assertThat(actual.getRoles().get(0).getName()).isEqualTo("ROLE_USER");
        assertThat(actual.getId()).isEqualTo(3L);
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
        assertThat(actual).hasSize(1);
    }
}
