package com.search.pharmacy.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.ws.model.OrderDTO;
import com.search.pharmacy.ws.model.OrderLineDTO;
import java.util.Arrays;
import java.util.HashMap;
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
public class OrderServiceTest {

  @Autowired private OrderService sut;

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_orders() {
    List<Order> actual = sut.getOrders();

    assertThat(actual).isNotNull();
    assertThat(actual).hasSize(1);
    assertThat(actual.get(0).getComment()).isEqualTo("ma commande");
    assertThat(actual.get(0).getStatus()).isEqualTo(Order.OrderStatus.EN_PREPARATION);
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
      scripts = "classpath:service/dropTestData.sql",
      executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_create_a_new_order() {

    // given
    OrderDTO orderDTO =
        OrderDTO.builder()
            .status(Order.OrderStatus.EN_PREPARATION.toString())
            .address("123 rue de la ville")
            .phoneNumber("0600000000")
            .userId(1L)
            .comment("ma commande")
            .build();

    List<OrderLineDTO> orderLineDTOS =
        Arrays.asList(
            OrderLineDTO.builder().quantity(1).medicineId(1L).build(),
            OrderLineDTO.builder().quantity(2).medicineId(2L).build());

    // when
    Order result = sut.createOrder(orderDTO, orderLineDTOS);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo(Order.OrderStatus.EN_PREPARATION);
    assertThat(result.getAddress()).isEqualTo("123 rue de la ville");
    assertThat(result.getPhoneNumber()).isEqualTo("0600000000");
    assertThat(result.getComment()).isEqualTo("ma commande");
    assertThat(result.getOrderLines()).hasSize(2);
    assertThat(result.getUser().getFirstName()).isEqualTo("Admin");
    assertThat(result.getOrderLines().get(0).getQuantity()).isEqualTo(1);
  }

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
          scripts = "classpath:service/dropTestData.sql",
          executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_partial_update_order() {

    // given
    Long orderId = 1L;
    Map<String, Object> fieldsToUpdate = new HashMap<>();
    fieldsToUpdate.put("status", "EXPEDIÉE");

    // when
    Order partialUpdatedOrder = sut.partialUpdateOrder(orderId, fieldsToUpdate);

    // then
    assertThat(partialUpdatedOrder).isNotNull();
    assertThat(partialUpdatedOrder.getStatus()).isEqualTo(Order.OrderStatus.EXPEDIÉE);
  }

/*  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
          scripts = "classpath:service/dropTestData.sql",
          executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_update_order() {

    // given
    Long orderId = 1L;
    OrderDTO orderDTO =
            OrderDTO.builder()
                    .status(Order.OrderStatus.EN_PREPARATION.toString())
                    .address("123 rue de la ville")
                    .phoneNumber("07080906")
                    .userId(1L)
                    .comment("ma commande updated")
                    .build();

    List<OrderLineDTO> orderLineDTOS =
            Arrays.asList(
                    OrderLineDTO.builder().quantity(1).medicineId(1L).build(),
                    OrderLineDTO.builder().quantity(2).medicineId(2L).build());

    // when
    Order updatedOrder = sut.updateOrder(orderId, orderDTO, orderLineDTOS);

    // then
    assertThat(updatedOrder).isNotNull();
    assertThat(updatedOrder.getStatus()).isEqualTo(Order.OrderStatus.EN_PREPARATION);
    assertThat(updatedOrder.getAddress()).isEqualTo("123 rue de la ville");
    assertThat(updatedOrder.getPhoneNumber()).isEqualTo("07080906");
    assertThat(updatedOrder.getComment()).isEqualTo("ma commande updated");
  }*/

  @Test
  @Sql(scripts = "classpath:service/datasets.sql")
  @Sql(
          scripts = "classpath:service/dropTestData.sql",
          executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_order_by_user_id() {

    // given
    Long userId = 1L;

    // when
    List<Order> orders = sut.getOrderByUserId(userId);

    // then
    assertThat(orders).isNotNull();
    assertThat(orders).hasSize(1);
    assertThat(orders.get(0).getComment()).isEqualTo("ma commande");
    assertThat(orders.get(0).getStatus()).isEqualTo(Order.OrderStatus.EN_PREPARATION);
    assertThat(orders.get(0).getUser().getFirstName()).isEqualTo("Admin");
    assertThat(orders.get(0).getOrderLines()).hasSize(1);
  }
}
