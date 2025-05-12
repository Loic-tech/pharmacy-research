package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.domain.model.OrderLine;
import com.search.pharmacy.domain.model.User;
import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.repository.OrderRepository;
import com.search.pharmacy.repository.UserRepository;
import com.search.pharmacy.ws.model.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
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

  public List<Order> getOrderByUserId(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    return optionalUser.map(user -> orderRepository.findAllByUserId(user.getId())).orElse(null);
  }

  public List<Order> getOrders() {
    return orderRepository.findAll();
  }
}
