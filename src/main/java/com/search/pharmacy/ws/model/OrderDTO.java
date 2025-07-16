package com.search.pharmacy.ws.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDTO {

  private Long id;

  private Long userId;

  @NotNull(message = "status cannot be null")
  private String status;

  private Double totalAmount;

  @NotNull(message = "address cannot be null")
  private String address;

  @NotNull(message = "phone number cannot be null")
  private String phoneNumber;

  private String comment;
  private LocalDateTime orderDate;
}
