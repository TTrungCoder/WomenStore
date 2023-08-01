package com.web.service.impl;


import com.web.domain.Authority;
import com.web.domain.CartItem;
import com.web.domain.Product;
import com.web.respository.CartItemRepository;
import com.web.respository.ProductRepository;
import com.web.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    CartItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<CartItem> findAll(Authority authority){
        return itemRepository.findByAuthority(authority);
    }

    @Override
    public CartItem addToCart(Long productId, Integer quantity, Authority authority) {
        Integer addedQuantity = quantity;

        Product product = productRepository.findById(productId).get();

        CartItem  cartItem =  itemRepository.findByAuthorityAndProduct(authority, product);

        if(cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
            cartItem.setTotalPrice(addedQuantity * product.getPrice());
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setAuthority(authority);
            cartItem.setProduct(product);
            cartItem.setTotalPrice(product.getPrice() * quantity);
        }

        return itemRepository.save(cartItem);
    }
    @Override
    @Transactional
    public void removeProduct(Authority authority, Long productId) {
        itemRepository.deleteByCustomerAndProduct(Long.valueOf(authority.getId()), productId);
    }
}