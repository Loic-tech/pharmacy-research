package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.Pharmacy;
import com.search.pharmacy.domain.model.Stock;
import com.search.pharmacy.repository.PharmacyRepository;
import com.search.pharmacy.repository.StockRepository;
import com.search.pharmacy.ws.mapper.StockMapper;
import com.search.pharmacy.ws.model.StockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class ExcelService {

  private final PharmacyRepository pharmacyRepository;
  private final StockRepository stockRepository;
  private final StockMapper stockMapper;

  @Transactional
  public void addPharmaciesListFromGouv() {
    String[] data;
    List<Pharmacy> pharmacies = new ArrayList<>();
    int lineNumber = 0;
    try {
      BufferedReader bufferedReader =
          new BufferedReader(
              new FileReader("src/main/java/com/search/pharmacy/service/carte-des-pharmacies-de-paris.csv"));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (lineNumber == 0) {
          lineNumber++;
          continue;
        }
        data = line.split(";");
        Pharmacy pharmacy =
            new Pharmacy(
                data[2],
                data[5] + data[6] + " " + data[7] + " " + data[8] + " " + data[12] + " " + data[13],
                data[14],
                Double.parseDouble(data[21]),
                Double.parseDouble(data[20]));
        pharmacies.add(pharmacy);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    pharmacyRepository.saveAll(pharmacies);
  }

  @Transactional
  public void addStockData() {
    String[] data;
    List<StockDTO> stocks = new ArrayList<>();
    List<Stock> stockList = new ArrayList<>();
    int lineNumber = 0;
    try {
      BufferedReader bufferedReader =
              new BufferedReader(
                      new FileReader("src/main/java/com/search/pharmacy/service/pharmacy_medicine_data.csv"));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (lineNumber == 0) {
          lineNumber++;
          continue;
        }
        data = line.split(",");
        StockDTO stockDTO = StockDTO.builder().idPharmacy(Long.parseLong(data[0]))
                .idMedicine(Long.parseLong(data[1]))
                .quantity(Integer.parseInt(data[2])).build();
        stocks.add(stockDTO);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    stocks.forEach(stockDTO -> stockList.add(stockMapper.toEntity(stockDTO)));
    stockRepository.saveAll(stockList);
  }
}
