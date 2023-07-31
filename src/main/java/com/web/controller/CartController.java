package com.web.controller;

import com.web.domain.Product;
import com.web.global.GlobalData;
import com.web.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.web.model.ProductDTO;
import com.web.service.CategoryService;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id) {
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }
    @GetMapping("product/{id}")
    public String view(@PathVariable ("id") Long id, Model model) {


        Optional<Product> opt = productService.findById(id);
        ProductDTO productDto = new ProductDTO();

        Product entity = opt.get();
        BeanUtils.copyProperties(entity, productDto);
        productDto.setCategoryId(Integer.valueOf(entity.getCategory().getId()));

        System.out.println(productDto);

        model.addAttribute("product", productDto);

        return "shop-details";
    }
    @RequestMapping("")
    public String getCartItems(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "shopping-cart";
    }
}
