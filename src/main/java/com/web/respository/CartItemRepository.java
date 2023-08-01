package com.web.respository;


import com.web.domain.Authority;
import com.web.domain.CartItem;
import com.web.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByAuthority(Authority authority);

    CartItem findByAuthorityAndProduct(Authority authority, Product product);


    @Query("DELETE FROM CartItem c WHERE c.authority.id = ?1 AND c.product.id = ?2")
    @Modifying
    void deleteByCustomerAndProduct(Long authorityId, Long productId);
}
