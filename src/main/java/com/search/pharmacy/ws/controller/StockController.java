package com.search.pharmacy.ws.controller;

import com.search.pharmacy.service.ExcelService;
import com.search.pharmacy.service.StockService;
import com.search.pharmacy.ws.model.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stocks")
@RequiredArgsConstructor
public class StockController {

  private final ExcelService excelService;
  private final StockService stockService;

  @PostMapping("/populate")
  public ResponseEntity<Void> populateData() {
    excelService.addStockData();
    return ResponseEntity.noContent().build();
  }

  @PostMapping("search")
  public ResponseEntity<List<ResponseDTO>> searchMedicine(
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "description", defaultValue = "") String description,
      @RequestParam(value = "latitude", defaultValue = "0") Double latitude,
      @RequestParam(value = "longitude", defaultValue = "0") Double longitude) {
      return ResponseEntity.ok(stockService.searchMedicine(name, description, latitude, longitude));
  }
}
