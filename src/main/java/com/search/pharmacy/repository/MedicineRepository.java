package com.search.pharmacy.repository;

import com.search.pharmacy.domain.model.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

  Page<Medicine> findAll(Pageable pageable);

  Page<Medicine> findAllByCategory_Id(Long categoryId, Pageable pageable);

  Page<Medicine> findAllByNameIgnoreCaseContaining(String name, Pageable pageable);

    Page<Medicine> findAllByNameIgnoreCaseContainingAndCategory_Id(String trim, Long categoryId, Pageable pageable);
}
