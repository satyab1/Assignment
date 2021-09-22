package com.assignment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.model.Product;
import com.assignment.service.ProductService;

//creating RestController

@RestController
public class ProductController {

	/* autowired the Product Service class */

	@Autowired
	ProductService productService;

	/*
	 * creating a get mapping that retrieves all the product detail from the
	 * in-Memory database
	 */

	@GetMapping("/products")
	private List<Product> getAllProduct() {
		return productService.getAllProducts();
	}

	/*
	 * creating get mapping that retrives filtered product detail in the in-Memory
	 * database
	 */

	@GetMapping("/product")
	private List<Product> getFilteredProduct(@RequestParam Map<String, String> allParams) {
		return productService.getFilteredproducts(allParams);

	}
}
