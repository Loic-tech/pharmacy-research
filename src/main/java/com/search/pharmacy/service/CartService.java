package com.search.pharmacy.service;


import com.search.pharmacy.repository.CartRepository;
import com.search.pharmacy.ws.mapper.CartMapper;
import com.search.pharmacy.ws.model.CartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartDTO createCart(CartDTO cartDTO) {
        return Optional.of(cartDTO)
                .map(cartMapper::toEntity)
                .map(cartRepository::save)
                .map(cartMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Could not create a new cart"));
    }

    public List<CartDTO> getCarts() {
        return cartRepository.findAll().stream().map(cartMapper::toDTO).toList();
    }
}
