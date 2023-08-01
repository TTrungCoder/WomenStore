package com.web.model;

import com.web.domain.Authority;
import com.web.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;

    private Authority authority;

    private Product product;

    private int quantity;

    private double totalPrice;
}
