package com.search.pharmacy.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.boot.test.context.TestConfiguration
@EnableWebSecurity
public class TestConfiguration {
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
    return dataSource;
  }

  @Bean
  public JpaProperties jpaProperties() {
    JpaProperties jpaProperties = new JpaProperties();
    jpaProperties.setShowSql(true);
    jpaProperties.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
    Map<String, String> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", "update");
    jpaProperties.setProperties(properties);

    return jpaProperties;
  }

  @Bean
  @Primary
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
    return http.build();
  }
}
