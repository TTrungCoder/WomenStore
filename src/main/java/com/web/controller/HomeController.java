package com.web.controller;


import com.web.domain.Product;
import com.web.service.CategoryService;
import com.web.service.ProductService;
import com.web.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {

	@Autowired
	private ProductService productService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("")
	public String index(ModelMap model) {
		List<Product> list = productService.getTop12ProductsByPrice();
		model.addAttribute("products", list);
		return "home";
	}

	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> saveFile(@PathVariable String filename){

		Resource file = storageService.loadAsResource(filename);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);

	}

	@GetMapping("shop")
	public String product(ModelMap model,
						  @RequestParam(value = "pageNumber", defaultValue = "0") Optional<Integer> pageNumber,
						  @RequestParam(defaultValue = "9") int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber.orElse(0), pageSize);

		Page<Product> page = productService.findAll(pageable);

		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("products", page.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", page.getTotalPages());

		return "shop";
	}

	@GetMapping("/shop/{categoryId}")
	public String findProductByCategoryID(ModelMap model,
										  @RequestParam(value = "pageNumber", defaultValue = "0") Optional<Integer> pageNumber,
										  @RequestParam(defaultValue = "9") int pageSize,
										  @PathVariable ("categoryId") Long id) {
		Pageable pageable = PageRequest.of(pageNumber.orElse(0), pageSize);

		Page<Product> page = productService.findProductByCategoryId(id, pageable);

		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("products", page.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", page.getTotalPages());
        return "shop";
	}

	
	@GetMapping("/blog")
	public String blog() {
		return "blog";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
}
