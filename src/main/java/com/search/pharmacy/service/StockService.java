package com.search.pharmacy.service;

import com.search.pharmacy.common.exception.InvalidParamException;
import com.search.pharmacy.repository.StockRepository;
import com.search.pharmacy.ws.mapper.StockMapper;
import com.search.pharmacy.ws.model.ResponseDTO;
import com.search.pharmacy.ws.model.StockDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.of;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public List<StockDTO> getStocks() {
        return stockRepository.findAll().stream().map(stockMapper::toDTO).toList();
    }

    public StockDTO createStock(StockDTO stockDTO) {
        return of(stockDTO)
                .map(stockMapper::toEntity)
                .map(stockRepository::save)
                .map(stockMapper::toDTO)
                .orElseThrow(
                        () -> {
                            log.debug("Could not create a new medicine");
                            return new InvalidParamException("Could not create a new stock");
                        });
    }

    public StockDTO getStock(Long stockId) {
        return stockRepository.findById(stockId)
                .map(stockMapper::toDTO)
                .orElseThrow(
                        () -> new InvalidParamException("Could not find stock with id: " + stockId));
    }

    @Transactional
    public List<ResponseDTO> searchMedicine(String medicineName, String description, Double latitude, Double longitude) {
        return stockRepository.findMedicamentInStock(medicineName, description, latitude, longitude);
    }
}
