package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.domain.model.OrderLine;
import com.search.pharmacy.domain.model.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderNotificationService {

    private final EmailService emailService;

    public void sendOrderConfirmationEmail(Order order) {
        try {
            User user = order.getUser();
            Context context = new Context();

            context.setVariable("orderNumber", order.getOrderNumber());
            context.setVariable("orderStatus", order.getStatus().toString());
            context.setVariable("orderDate", order.getOrderDate());
            context.setVariable("totalAmount", order.getTotalAmount());
            context.setVariable("customerName", user.getFirstName() + " " + user.getLastName());

            List<Map<String, Object>> orderItems = order.getOrderLines().stream()
                    .map(this::convertOrderLineToMap)
                    .collect(Collectors.toList());

            context.setVariable("orderItems", orderItems);

            emailService.sendEmail(
                    user.getEmail(),
                    "Confirmation de votre commande #" + order.getOrderNumber(),
                    "order-confirmation",
                    context
            );

            log.info("Order confirmation email sent for order #{}", order.getOrderNumber());
        } catch (MessagingException e) {
            log.error("Failed to send order confirmation email for order #{}", order.getOrderNumber(), e);
        }
    }


    private Map<String, Object> convertOrderLineToMap(OrderLine orderLine) {
        Map<String, Object> item = new HashMap<>();
        item.put("productName", orderLine.getMedicine().getName());
        item.put("productImage", orderLine.getMedicine().getUrl());
        item.put("quantity", orderLine.getQuantity());
        item.put("price", orderLine.getMedicine().getNewPrice());
        item.put("totalPrice", orderLine.getQuantity() * orderLine.getMedicine().getNewPrice());
        return item;
    }

}
