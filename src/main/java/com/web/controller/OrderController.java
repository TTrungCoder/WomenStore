package com.web.controller;

import com.web.domain.Account;
import com.web.domain.CartItem;
import com.web.domain.Order;
import com.web.domain.OrderDetail;
import com.web.service.AccountService;
import com.web.service.OrderService;
import com.web.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("")
    public String orderList(Model model, HttpSession session, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Order> order = orderService.findOrdersByAccount(userDetails);
        model.addAttribute("order", order);
        return "/order-list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
        return "/checkout";
    }

    @PostMapping("purchase")
    public String order(@RequestParam("address") String address,
                        @RequestParam("phone") String phone,
                        HttpSession session,
                        Model model, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        Optional<Account> account = accountService.findById(username);

        List<CartItem> cartItems = shoppingCartService.findAll(userDetails);
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("message", "Giỏ hàng trống. Vui lòng thêm sản phẩm vào giỏ hàng trước khi đặt hàng.");
            return "shopping-cart";
        }

        // Tính tổng số tiền
        double totalPrice = shoppingCartService.calculateTotalPrice(cartItems);

        // Tạo đơn hàng và lưu vào cơ sở dữ liệu (hoặc thực hiện các thao tác khác liên quan đến đặt hàng)
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setAmount(totalPrice);
        order.setStatus("Pending"); // Trạng thái mặc định của đơn hàng
        order.setAddress(address);
        order.setPhone(phone);
        order.setAccount(account.get());

        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setUnitPrice(cartItem.getProduct().getPrice());
            orderService.addOrderDetail(orderDetail);
        }

        // Lưu đơn hàng vào cơ sở dữ liệu (hoặc thực hiện các thao tác khác liên quan đến đặt hàng)
        orderService.saveOrder(order);

        // Xóa giỏ hàng sau khi đã đặt hàng thành công
        shoppingCartService.clearCart(userDetails);

        model.addAttribute("message", "Đặt hàng thành công! Cảm ơn bạn đã mua hàng.");
        return "forward:/order";
    }
}
