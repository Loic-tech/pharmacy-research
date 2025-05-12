package com.search.pharmacy.ws.controller;

import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.service.OrderService;
import com.search.pharmacy.service.TokenStorageService;
import com.search.pharmacy.ws.model.CreateOrderRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final TokenStorageService tokenStorageService;

  @PostMapping
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
    Order createdOrder = orderService.createOrder(request.getOrder(), request.getOrderLines());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }

  @GetMapping("{userId}")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<Order>> getOrderFromUser(@PathVariable Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderByUserId(userId));
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<Order>> getOrders() {
    return ResponseEntity.ok(orderService.getOrders());
  }
}
