package com.search.pharmacy.ws.controller;

import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.service.OrderService;
import com.search.pharmacy.ws.model.CreateOrderRequest;
import com.search.pharmacy.ws.model.OrderDTO;
import com.search.pharmacy.ws.model.OrderLineDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
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

  @PostMapping
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
    Order createdOrder = orderService.createOrder(request.getOrder(), request.getOrderLines());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }

  /**
   * Mettre à jour complètement une commande existante
   *
   * @param orderId ID de la commande à mettre à jour
   * @param orderDTO Nouvelles informations de la commande
   * @param orderLines Nouvelles lignes de commande
   * @return Commande mise à jour
   */
  @PutMapping("/{orderId}")
  public ResponseEntity<Order> updateOrder(
      @PathVariable Long orderId,
      @Valid @RequestBody OrderDTO orderDTO,
      @RequestParam(required = false) List<OrderLineDTO> orderLines) {
    if (orderLines == null || orderLines.isEmpty()) {
      throw new IllegalArgumentException("Au moins une ligne de commande est requise");
    }

    Order updatedOrder = orderService.updateOrder(orderId, orderDTO, orderLines);
    return ResponseEntity.ok(updatedOrder);
  }

  /**
   * Mettre à jour partiellement une commande existante
   *
   * @param orderId ID de la commande à mettre à jour
   * @param updates Map des champs à mettre à jour
   * @return Commande mise à jour
   */
  @PatchMapping("/{orderId}")
  public ResponseEntity<Order> partialUpdateOrder(
      @PathVariable Long orderId, @RequestBody(required = false) Map<String, Object> updates) {
    if (updates == null || updates.isEmpty()) {
      throw new IllegalArgumentException("Au moins un champ à mettre à jour est requis");
    }

    Order updatedOrder = orderService.partialUpdateOrder(orderId, updates);
    return ResponseEntity.ok(updatedOrder);
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
