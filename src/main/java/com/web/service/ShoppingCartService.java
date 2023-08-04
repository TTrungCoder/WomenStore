package com.web.service;

import com.web.domain.Account;
import com.web.domain.CartItem;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ShoppingCartService {
    List<CartItem> findAll(UserDetails userDetails);

    CartItem addToCart(Long productId, Integer quantity, UserDetails userDetails);

    void removeProduct(UserDetails userDetails, Long productId);
}
