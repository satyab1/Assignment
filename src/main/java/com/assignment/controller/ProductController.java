package com.assignment.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.model.Product;
import com.assignment.service.ProductService;
import com.assignment.util.RecordNotFoundException;

//creating RestControllers

@RestController
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	/* autowired the Product Service class */

	@Autowired
	ProductService productService;

	/*
	 * creating a get mapping that retrieves all the product detail from the
	 * in-Memory database
	 */

	@GetMapping("/products")
	private ResponseEntity<List<Product>> getAllProducts() {

		LOGGER.info("Processing request to get all records ");
		List<Product> products = productService.getAllProducts();
		if (products.isEmpty()) {
			throw new RecordNotFoundException("No records found");
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	/*
	 * creating get mapping that retrives filtered product detail in the in-Memory
	 * database
	 */

	@GetMapping("/product")
	private ResponseEntity<List<Product>> getFilteredProducts(@RequestParam Map<String, String> inputParams) {
		LOGGER.info("Processing request with input params ");
		List<Product> products = productService.getFilteredproducts(inputParams);
		if (products.isEmpty()) {
			throw new RecordNotFoundException("No records found for input params ..");
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

	}
}
