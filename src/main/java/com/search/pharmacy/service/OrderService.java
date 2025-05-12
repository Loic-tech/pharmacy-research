package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.*;
import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.repository.OrderRepository;
import com.search.pharmacy.repository.UserRepository;
import com.search.pharmacy.ws.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

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

  @Transactional
  public Order updateOrder(Long orderId, OrderDTO orderDTO, List<OrderLineDTO> orderLines) {
    Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Commande non trouvée avec l'ID: " + orderId));

    if (existingOrder.getStatus() == Order.OrderStatus.LIVREE ||
            existingOrder.getStatus() == Order.OrderStatus.ANNULEE) {
      throw new IllegalStateException("Impossible de modifier une commande déjà livrée ou annulée");
    }

    existingOrder.setAddress(orderDTO.getAddress());
    existingOrder.setPhoneNumber(orderDTO.getPhoneNumber());
    existingOrder.setComment(orderDTO.getComment());

    if (orderDTO.getStatus() != null) {
      existingOrder.setStatus(Order.OrderStatus.valueOf(orderDTO.getStatus()));
    }

    if (orderDTO.getUserId() != null) {
      User user = userRepository.findById(orderDTO.getUserId())
              .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
      existingOrder.setUser(user);
    }

    existingOrder.getOrderLines().clear();

    double totalAmount = 0.0;
    List<OrderLine> newOrderLines = new ArrayList<>();

    for (OrderLineDTO lineDTO : orderLines) {
      Medicine medicine = medicineRepository
              .findById(lineDTO.getMedicineId())
              .orElseThrow(() -> new EntityNotFoundException("Médicament non trouvé avec ID: " + lineDTO.getMedicineId()));

      OrderLine orderLine = new OrderLine();
      orderLine.setMedicine(medicine);
      orderLine.setQuantity(lineDTO.getQuantity());
      orderLine.setOrder(existingOrder);

      double lineTotal = medicine.getNewPrice() * lineDTO.getQuantity();
      totalAmount += lineTotal;

      newOrderLines.add(orderLine);
    }

    existingOrder.setOrderLines(newOrderLines);
    existingOrder.setTotalAmount(totalAmount);

      return orderRepository.save(existingOrder);
  }

  @Transactional
  public Order partialUpdateOrder(Long orderId, Map<String, Object> updates) {
    Order existingOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Commande non trouvée avec l'ID: " + orderId));

    if (existingOrder.getStatus() == Order.OrderStatus.LIVREE ||
            existingOrder.getStatus() == Order.OrderStatus.ANNULEE) {
      throw new IllegalStateException("Impossible de modifier une commande déjà livrée ou annulée");
    }

    if (updates.containsKey("address")) {
      existingOrder.setAddress((String) updates.get("address"));
    }

    if (updates.containsKey("phoneNumber")) {
      existingOrder.setPhoneNumber((String) updates.get("phoneNumber"));
    }

    if (updates.containsKey("comment")) {
      existingOrder.setComment((String) updates.get("comment"));
    }

    if (updates.containsKey("status")) {
      String statusStr = (String) updates.get("status");
      existingOrder.setStatus(Order.OrderStatus.valueOf(statusStr));
    }

    if (updates.containsKey("userId")) {
      Long userId = Long.valueOf(updates.get("userId").toString());
      User user = userRepository.findById(userId)
              .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
      existingOrder.setUser(user);
    }

    return orderRepository.save(existingOrder);
  }

  public List<Order> getOrderByUserId(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    return optionalUser.map(user -> orderRepository.findAllByUserId(user.getId())).orElse(null);
  }

  public List<Order> getOrders() {
    return orderRepository.findAll();
  }
}
