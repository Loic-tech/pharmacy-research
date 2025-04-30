package com.search.pharmacy.ws.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {

    private Long id;
    private List<CartDTO> carts;
    private UserDTO user;
    private String status;
    private Double totalAmount;
    private String address;
    private String phoneNumber;
    private String comment;
    private LocalDateTime orderDate;
}
