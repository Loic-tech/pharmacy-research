package com.search.pharmacy.ws.controller;

import com.search.pharmacy.common.exception.orm.UnauthenticatedUserException;
import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.service.OrderService;
import com.search.pharmacy.service.TokenStorageService;
import com.search.pharmacy.ws.model.CreateOrderRequest;
import com.search.pharmacy.ws.model.OrderDTO;
import com.search.pharmacy.ws.model.OrderLineDTO;
import com.search.pharmacy.ws.model.OrderSummaryDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

/*  @PostMapping("/after-auth")
  public ResponseEntity<?> completeOrder(@RequestParam String orderToken) {
    // Récupérer la commande stockée temporairement
    OrderDTO pendingOrder = tokenStorageService.retrieveOrder(orderToken);

    if (pendingOrder == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of("message", "No pending order found with this token"));
    }

    // Supprimer la commande temporaire
    tokenStorageService.removeOrder(orderToken);

    // Créer la commande avec l'utilisateur maintenant authentifié
    OrderSummaryDTO createdOrder = orderService.createOrder(pendingOrder);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }*/

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<Order>> getOrders() {
    return ResponseEntity.ok(orderService.getOrders());
  }
}
