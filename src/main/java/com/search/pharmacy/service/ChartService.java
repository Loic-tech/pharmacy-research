package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.Order;
import com.search.pharmacy.repository.MedicineRepository;
import com.search.pharmacy.ws.model.ChartDTO;
import com.search.pharmacy.ws.model.ChartOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final UserService userService;
    private final MedicineRepository medicineRepository;
    private final OrderService orderService;

    public ChartDTO getChartInfo() {

        Integer usersTotalNumber = userService.getUsers().size();
        Integer totalMedicines = medicineRepository.findAll().size();
        Integer totalOrders = orderService.getOrders().size();
        List<Order> lastFiveOrders = orderService.getOrders().stream().skip(Math.max(0, orderService.getOrders().size() - 5)).toList();
        List<ChartOrderDTO> chartOrderDTOList = new ArrayList<>();

        lastFiveOrders.forEach(order -> {
            ChartOrderDTO chartOrderDTO = ChartOrderDTO.builder()
                    .id(order.getId())
                    .clientName(order.getUser().getFirstName() + " " + order.getUser().getLastName())
                    .totalPrice(order.getTotalAmount())
                    .orderDate(order.getOrderDate())
                    .build();
            chartOrderDTOList.add(chartOrderDTO);
        });

        return ChartDTO.builder()
                .totalUsers(usersTotalNumber)
                .totalMedicines(totalMedicines)
                .totalOrders(totalOrders)
                .lastFiveOrders(chartOrderDTOList)
                .build();

    }
}
