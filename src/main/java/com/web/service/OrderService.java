package com.web.service;


import com.web.domain.Account;
import com.web.domain.Order;
import com.web.domain.OrderDetail;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderService {
    @Transactional
    void saveOrder(Order order);

    void addOrderDetail(OrderDetail orderDetail);

    List<Order> findOrdersByAccount(UserDetails userDetails);

}