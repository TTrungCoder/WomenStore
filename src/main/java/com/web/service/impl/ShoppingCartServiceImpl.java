package com.web.service.impl;


import com.web.domain.Account;
import com.web.domain.CartItem;
import com.web.domain.Product;
import com.web.respository.CartItemRepository;
import com.web.respository.ProductRepository;
import com.web.service.AccountService;
import com.web.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    CartItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AccountService accountService;

    @Override
    public List<CartItem> findAll(UserDetails userDetails) {
        // Lấy tên người dùng từ UserDetails
        String username = userDetails.getUsername();

        // Sử dụng tên người dùng để lấy thông tin Account
        Optional<Account> account = accountService.findById(username);

        // Sử dụng thông tin Account để truy vấn danh sách CartItem
        return itemRepository.findByAccount(account.get());
    }


    @Override
    public CartItem addToCart(Long productId, Integer quantity, UserDetails userDetails) {
        // Lấy tên người dùng từ UserDetails
        String username = userDetails.getUsername();

        // Sử dụng tên người dùng để lấy thông tin Account
        Optional<Account> account = accountService.findById(username);


        Integer addedQuantity = quantity;

        Product product = productRepository.findById(productId).get();

        CartItem  cartItem =  itemRepository.findByAccountAndProduct(account.get(), product);

        if(cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
            cartItem.setTotalPrice(addedQuantity * product.getPrice());
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setAccount(account.get());
            cartItem.setProduct(product);
            cartItem.setTotalPrice(product.getPrice() * quantity);
        }

        return itemRepository.save(cartItem);
    }
    @Override
    @Transactional
    public void removeProduct(UserDetails userDetails, Long productId) {
        String username = userDetails.getUsername();
        itemRepository.deleteByAccountAndProduct(username, productId);
    }
}