package com.search.pharmacy.ws.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
public class CreateOrderRequest {
    private OrderDTO order;
    private List<OrderLineDTO> orderLines;

}
