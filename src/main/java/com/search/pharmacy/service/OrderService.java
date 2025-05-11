package com.search.pharmacy.service;

import com.search.pharmacy.common.exception.orm.UnauthenticatedUserException;
import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.domain.model.OrderLine;
import com.search.pharmacy.domain.model.User;
import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.repository.OrderRepository;
import com.search.pharmacy.repository.UserRepository;
import com.search.pharmacy.ws.mapper.MedicineMapper;
import com.search.pharmacy.ws.mapper.UserMapper;
import com.search.pharmacy.ws.model.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
  private final OAuth2AuthorizedClientManager authorizedClientManager;
  private final UserRepository userRepository;
  private final MedicineRepository medicineRepository;

  @Transactional
  public Order createOrder(OrderDTO orderDTO, List<OrderLineDTO> orderLines) {
    Order order = new Order();

    Optional<User> optionalUser = userRepository.findById(orderDTO.getUserId());
    optionalUser.ifPresent(order::setUser);

    order.setAddress(orderDTO.getAddress());
    order.setStatus(Order.OrderStatus.valueOf(orderDTO.getStatus()));
    order.setPhoneNumber(orderDTO.getPhoneNumber());
    order.setComment(orderDTO.getComment());

    Order savedOrder = orderRepository.save(order);

    for (OrderLineDTO orderLineDTO : orderLines) {
      Optional<Medicine> medicine =
          Optional.ofNullable(
              medicineRepository
                  .findById(orderLineDTO.getMedicineId())
                  .orElseThrow(RuntimeException::new));

      OrderLine orderLine =
          OrderLine.builder().medicine(medicine.get()).quantity(orderLineDTO.getQuantity()).build();

      savedOrder.addOrderLine(orderLine);
    }
    return orderRepository.save(savedOrder);
  }

  public List<Order> getOrders() {
    return orderRepository.findAll();
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
