package com.search.pharmacy.ws.controller;

import com.search.pharmacy.service.ChartService;
import com.search.pharmacy.ws.model.ChartDTO;
import com.search.pharmacy.ws.model.ChartOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/charts")
public class ChartController {

    private final ChartService chartService;

    @GetMapping
    public ResponseEntity<ChartDTO> getChartDTO() {
        return ResponseEntity.ok(chartService.getChartInfo());
    }
}
