package com.search.pharmacy.service;

import com.search.pharmacy.repository.PharmacyRepository;
import com.search.pharmacy.ws.model.PharmacyDTO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PharmacyServiceIT {

  @Autowired private PharmacyRepository pharmacyRepository;
  @Autowired private PharmacyService sut;
  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  @Sql(scripts = "classpath:service/test_it_pharmacy_service.sql")
  @Sql(
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
      scripts = "classpath:service/dropTestData.sql")
  public void should_return_all_pharmacies() {
    // When
    List<PharmacyDTO> actual = sut.getPharmacies();

    // Then
    assertThat(actual).hasSize(3);
  }

  @Test
  @Sql(scripts = "classpath:service/test_it_pharmacy_service.sql")
  @Sql(
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
      scripts = "classpath:service/dropTestData.sql")
  public void should_create_a_new_pharmacy() {
    // Given
    PharmacyDTO newPharmacyDTO =
        PharmacyDTO.builder()
            .name("Pharmacie Lafayatte")
            .address("10 rue du Docteur Calmette")
            .contact("01 02 03 04 05 06")
            .latitude(56.265)
            .longitude(45.256)
            .build();

    // When
    PharmacyDTO pharmacyDTO = sut.createPharmacy(newPharmacyDTO);

    // Then
    assertThat(pharmacyDTO).isNotNull();
    assertThat(pharmacyDTO.getName()).isEqualTo("Pharmacie Lafayatte");
    assertThat(pharmacyDTO.getAddress()).isEqualTo("10 rue du Docteur Calmette");
    assertThat(pharmacyDTO.getContact()).isEqualTo("01 02 03 04 05 06");
    assertThat(pharmacyDTO.getLatitude()).isEqualTo(56.265);
    assertThat(pharmacyDTO.getLongitude()).isEqualTo(45.256);
  }
}
