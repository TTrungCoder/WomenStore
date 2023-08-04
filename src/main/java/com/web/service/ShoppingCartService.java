package com.web.service;

import com.web.domain.Account;
import com.web.domain.CartItem;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.List;

public interface ShoppingCartService {
    List<CartItem> findAll(UserDetails userDetails);

    CartItem addToCart(Long productId, Integer quantity, UserDetails userDetails);

    double calculateTotalPrice(List<CartItem> cartItems);
    void removeProduct(UserDetails userDetails, Long productId);

    @Transactional
    void clearCart(UserDetails userDetails);
}
