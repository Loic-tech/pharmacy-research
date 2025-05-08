package com.search.pharmacy.ws.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderSummaryDTO {

    private List<MedicineListDTO> medicines;
    private UserDTO user;

    @NotNull(message = "status cannot be null")
    private String status;

    @NotNull(message = "total amount cannot be null")
    private Double totalAmount;

    @NotNull(message = "address cannot be null")
    private String address;

    @NotNull(message = "phone number cannot be null")
    private String phoneNumber;

    private String comment;
    private LocalDateTime orderDate;
}
