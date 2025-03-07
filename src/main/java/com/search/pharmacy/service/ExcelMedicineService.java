package com.search.pharmacy.service;

import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.repository.MedicineRepository;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExcelMedicineService {

  private final MedicineRepository medicineRepository;

  private static final String NAME =
      "src/main/java/com/search/pharmacy/service/2024-01-a-06_medic-am-par-type-de-prescripteur_serie-mensuelle.xls";

  @Transactional
  public void populateDBWithMedicine() {
    List<Medicine> medicines = new ArrayList<>();

    try {
      FileInputStream file = new FileInputStream(NAME);
      Workbook workbook = new HSSFWorkbook(file);

      Sheet sheet = workbook.getSheetAt(0);
      for (Row row : sheet) {
        if (row.getRowNum() == 0) continue;

        String name = row.getCell(1).getStringCellValue();
        String description = row.getCell(8).getStringCellValue();
        Medicine medicine = new Medicine(name, description);
        medicines.add(medicine);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    medicineRepository.saveAll(medicines);
  }
}
