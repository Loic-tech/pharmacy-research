package com.search.pharmacy.ws.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
public class CreateOrderRequest {
  @NotNull(message = "order cannot be null")
  private OrderDTO order;

  @NotNull(message = "orderLines cannot be null")
  private List<OrderLineDTO> orderLines;
}
