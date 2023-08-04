package com.web.controller;

import com.web.domain.Account;
import com.web.domain.CartItem;
import com.web.domain.Product;
import com.web.service.ProductService;
import com.web.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.web.model.ProductDTO;
import com.web.service.CategoryService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("")
    public String cart(Model model,Authentication authentication){

        HttpSession session = request.getSession();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        List<CartItem> cartItems = shoppingCartService.findAll(userDetails);

        if(cartItems == null) {
            model.addAttribute("check");
        } else {
            double totalPrice = cartItems.stream()
                    .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                    .sum();
            model.addAttribute("grandTotal", totalPrice);
            session.setAttribute("cartCount", cartItems.size());
            model.addAttribute("cart", cartItems);
        }

        return "shopping-cart";
    }
    @GetMapping("addToCart/{id}")
    public String addItemToCart(
            @PathVariable("id") Long productId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            HttpSession session,
            Model model,
            Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        CartItem cartItem = shoppingCartService.addToCart(productId, quantity, userDetails);

        model.addAttribute("cart", cartItem);
        return "redirect:/shop";
    }
    @GetMapping("product/{id}")
    public String view(@PathVariable ("id") Long id, Model model) {


        Optional<Product> opt = productService.findById(id);
        ProductDTO productDto = new ProductDTO();

        Product entity = opt.get();
        BeanUtils.copyProperties(entity, productDto);
        productDto.setCategoryId(Long.valueOf(entity.getCategory().getId()));

        System.out.println(productDto);

        model.addAttribute("product", productDto);

        return "shop-details";
    }

    @GetMapping("delete/{id}")
    public String deleteItemToCart(
            @PathVariable("id") Long productId,
            HttpSession session,
            Model model,
            Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();


        shoppingCartService.removeProduct(userDetails, productId);

        model.addAttribute("m", "Delete was successed");
        return "forward:/cart";
    }
}
