package com.search.pharmacy.ws.controller;

import com.search.pharmacy.common.exception.orm.UnauthenticatedUserException;
import com.search.pharmacy.service.OrderService;
import com.search.pharmacy.service.TokenStorageService;
import com.search.pharmacy.ws.model.OrderDTO;
import com.search.pharmacy.ws.model.OrderSummaryDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final TokenStorageService tokenStorageService;

  @PostMapping
  public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
    try {
      OrderSummaryDTO createdOrder = orderService.createOrder(orderDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    } catch (UnauthenticatedUserException e) {
      String orderToken = UUID.randomUUID().toString();
      tokenStorageService.storeOrder(orderToken, orderDTO);
      Map<String, Object> response = new HashMap<>();
      response.put("message", "Authentication required");
      response.put("orderToken", orderToken);
      response.put("authUrl", "https://accounts.google.com/o/oauth2/auth");

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
  }

  @PostMapping("/after-auth")
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
  }

  @GetMapping
  public ResponseEntity<List<OrderSummaryDTO>> getOrders() {
    return ResponseEntity.ok(orderService.getOrders());
  }
}
