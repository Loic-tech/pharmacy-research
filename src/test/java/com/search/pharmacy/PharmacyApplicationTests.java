package com.search.pharmacy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "spring.profiles.active:test")
class PharmacyApplicationTests {

	@Test
	void contextLoads() {
	}

}
