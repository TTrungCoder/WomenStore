package com.web.respository;


import com.web.domain.Account;
import com.web.domain.CartItem;
import com.web.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByAccount(Account account);

    CartItem findByAccountAndProduct(Account account, Product product);


    @Query("DELETE FROM CartItem c WHERE c.account.username = ?1 AND c.product.id = ?2")
    @Modifying
    void deleteByAccountAndProduct(String username, Long productId);
}
