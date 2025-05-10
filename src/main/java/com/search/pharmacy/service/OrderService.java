package com.search.pharmacy.service;

import com.search.pharmacy.common.exception.orm.UnauthenticatedUserException;
import com.search.pharmacy.repository.CartRepository;
import com.search.pharmacy.repository.OrderRepository;
import com.search.pharmacy.ws.mapper.OrderMapper;
import com.search.pharmacy.ws.mapper.OrderSummaryMapper;
import com.search.pharmacy.ws.model.*;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final OrderSummaryMapper orderSummaryMapper;
  private final OAuth2AuthorizedClientManager authorizedClientManager;
  private final CartRepository cartRepository;

  @Transactional
  public OrderSummaryDTO createOrder(OrderDTO orderDTO) {
    calculateTotalAmount(orderDTO);
    return Optional.of(orderDTO)
        .map(this::calculateTotalAmount)
        .map(orderMapper::toEntity)
            .map(order -> {
                cartRepository.saveAll(order.getCarts());
              return order;
            })
        .map(orderRepository::save)
        .map(orderSummaryMapper::toDTO)
        .orElseThrow(() -> new UnauthenticatedUserException("Could not create a new order"));
  }

  public List<OrderSummaryDTO> getOrders() {
    return orderRepository.findAll().stream().map(orderSummaryMapper::toDTO).toList();
  }

  public OrderDTO calculateTotalAmount(OrderDTO orderDTO) {
    if (orderDTO.getCart() == null || orderDTO.getCart().isEmpty()) {
      orderDTO.setTotalAmount(0.0);
    }

    double total = 0.0;
    for (CartDTO cartItem : orderDTO.getCart()) {
      MedicineListDTO medicine = cartItem.getMedicine();
      Integer quantity = cartItem.getQuantity();

      if (medicine != null && medicine.getNewPrice() != null && quantity != null) {
        total += medicine.getNewPrice() * quantity;
      }
    }

    orderDTO.setTotalAmount(total);
    orderDTO.setOrderDate(LocalDateTime.now());
    return orderDTO;
  }

  public String getCurrentUserToken() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
      return null;
    }

    OAuth2AuthorizeRequest authorizeRequest =
        OAuth2AuthorizeRequest.withClientRegistrationId(
                oauthToken.getAuthorizedClientRegistrationId())
            .principal(authentication)
            .build();

    OAuth2AuthorizedClient client = authorizedClientManager.authorize(authorizeRequest);
    if (client == null) {
      return null;
    }

    return client.getAccessToken().getTokenValue();
  }

  private UserDTO getCurrentAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null
        || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
      throw new UnauthenticatedUserException(
          "Vous devez être connecté pour effectuer cette action");
    }

    Object principal = authentication.getPrincipal();

    String email = null;
    String name = null;

    if (principal instanceof OAuth2User oauth2User) {
      email = oauth2User.getAttribute("email");
      name = oauth2User.getAttribute("name");
    }

    if (email == null) {
      throw new IllegalStateException("Impossible de récupérer l'email de l'utilisateur");
    }

    return UserDTO.builder().email(email).firstName(name).build();
  }
}
