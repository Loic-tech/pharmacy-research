package com.search.pharmacy.service;

import com.search.pharmacy.repository.CartRepository;
import com.search.pharmacy.ws.mapper.CartMapper;
import com.search.pharmacy.ws.model.CartDTO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final CartMapper mapper;

  public CartDTO createCart(CartDTO cartDTO) {
    return Optional.of(cartDTO)
        .map(mapper::toEntity)
        .map(cartRepository::save)
        .map(mapper::toDTO)
        .orElseThrow(() -> new RuntimeException("Could not create a new cart"));
  }

  public CartDTO getCart(Long id) {
    return cartRepository.findById(id).map(mapper::toDTO).orElseThrow();
  }

  public CartDTO updateCart(Long id, CartDTO cartDTO) {
    return Optional.of(cartDTO)
        .map(mapper::toEntity)
        .map(cartRepository::save)
        .map(mapper::toDTO)
        .orElseThrow(() -> new RuntimeException("Could not update a cart"));
  }

  public void deleteCart(Long id) {
    cartRepository.deleteById(id);
  }
}
