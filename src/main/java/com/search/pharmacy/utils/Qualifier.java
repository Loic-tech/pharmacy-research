package com.search.pharmacy.utils;

import com.search.pharmacy.domain.model.Medicine;
import com.search.pharmacy.domain.model.Pharmacy;
import com.search.pharmacy.domain.model.Stock;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Named("Qualifier")
public class Qualifier {

  @Named("IdToPharmacy")
  public Pharmacy idToPharmacy(Long id) {
    Pharmacy pharmacy = null;
    if (!Objects.isNull(id)) {
      pharmacy = new Pharmacy();
      pharmacy.setId(id);
    }
    return pharmacy;
  }

  @Named("IdToMedicine")
  public Medicine idToMedicine(Long id) {
    Medicine medicine = null;
    if (!Objects.isNull(id)) {
      medicine = new Medicine();
      medicine.setId(id);
    }
    return medicine;
  }
}
