package com.search.pharmacy.repository;

import com.search.pharmacy.domain.model.Order;

import java.util.List;
import java.util.Optional;

import com.search.pharmacy.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findAllByUserId(Long userId);
}
