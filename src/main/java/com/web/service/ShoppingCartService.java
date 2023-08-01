package com.web.service;

import com.web.domain.Authority;
import com.web.domain.CartItem;

import java.util.List;

public interface ShoppingCartService {
    List<CartItem> findAll(Authority authority);

    CartItem addToCart(Long productId, Integer quantity, Authority authority);

    void removeProduct(Authority authority, Long productId);
}
