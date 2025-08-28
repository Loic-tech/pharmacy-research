package com.search.pharmacy.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartDTO {

    private Integer totalUsers;
    private Integer totalMedicines;
    private Integer totalOrders;
    private List<ChartOrderDTO> lastFiveOrders;
}
