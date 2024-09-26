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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PharmacyServiceIT {

    @Autowired private PharmacyRepository pharmacyRepository;
    @Autowired private PharmacyService sut;
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    @Sql(scripts = "classpath:service/test_it_pharmacy_service.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
        scripts = "classpath:service/dropTestData.sql")
    public void should_return_all_pharmacies() {
        // When
        List<PharmacyDTO> actual = sut.getPharmacies();

        // Then
        assertThat(actual).hasSize(3);
    }
}
