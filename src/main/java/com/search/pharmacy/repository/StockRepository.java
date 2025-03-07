package com.search.pharmacy.repository;

import com.search.pharmacy.domain.model.Stock;
import com.search.pharmacy.ws.model.ResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

  @Query(
      """
    SELECT new com.search.pharmacy.ws.model.ResponseDTO(m.name,
    m.description,
    p.address,
    s.quantity,
        (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(p.latitude))
              * COS(RADIANS(p.longitude) - RADIANS(:longitude))
              + SIN(RADIANS(:latitude)) * SIN(RADIANS(p.latitude)))))
    FROM Stock s
    INNER JOIN Medicine m ON s.medicine.id = m.id
    INNER JOIN Pharmacy p ON s.pharmacy.id = p.id
    WHERE LOWER(m.name) ILIKE LOWER(CONCAT('%', :name, '%'))
      AND LOWER(m.description) ILIKE LOWER(CONCAT('%', :description, '%'))
      ORDER BY (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(p.latitude))
                         * COS(RADIANS(p.longitude) - RADIANS(:longitude))
                         + SIN(RADIANS(:latitude)) * SIN(RADIANS(p.latitude))))
               ASC
""")
  List<ResponseDTO> findMedicamentInStock(
      @Param("name") String name,
      @Param("description") String description,
      @Param("latitude") Double latitude,
      @Param("longitude") Double longitude);
}
