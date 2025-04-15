package com.search.pharmacy.utils;

import com.search.pharmacy.domain.model.Medicine;
import java.util.Objects;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@Named("Qualifier")
public class Qualifier {

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
