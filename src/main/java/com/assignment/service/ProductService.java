package com.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.assignment.model.Product;
import com.assignment.repository.ProductRepository;

@Service
public class ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ReadCSvFile csvreader;

	/* reading CSV file and placing in in-memory database (H2) */

	@PostConstruct
	private void postConstruct() {
		LOGGER.info("Loading CSV file activity started .....");
		List<Product> products = csvreader.processInputFile("data.csv");
		for (Product product : products) {
			productRepository.save(product);
		}
		LOGGER.info("CSV file has been saved successfully in-memory database ");
	}

	// getting all product records
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<Product>();
		try {
			productRepository.findAll().forEach(product -> products.add(product));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return products;
	}

	/* reading input request params and setting to query */
	public List<Product> getFilteredproducts(Map<String, String> allParams) {
		LOGGER.info("Regading input parameters from request..");
		String type = null;
		double min_price = 0;
		double max_price = 0;
		String city = null;
		String properties = null;

		for (Map.Entry<String, String> entry : allParams.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("type"))
				type = entry.getValue();
			if (entry.getKey().equalsIgnoreCase("min_price"))
				min_price = Double.parseDouble(entry.getValue());
			if (entry.getKey().equalsIgnoreCase("max_price"))
				max_price = Double.parseDouble(entry.getValue());
			if (entry.getKey().equalsIgnoreCase("city"))
				city = entry.getValue();
			if (entry.getKey().equalsIgnoreCase("properties"))
				properties = entry.getValue();
		}
		return findByLikeAndBetweenCriteria(type, min_price, max_price, city, properties);
	}

	/*
	 * Using predicates and Criteria API to filter data from database based on
	 * inputs provided in request object
	 * Using Criteria API we build query dynamically and execute and retrive data from database .
	 */
	public List<Product> findByLikeAndBetweenCriteria(String type, double min_price, double max_price, String city,
			String properties) {
		
		LOGGER.info("Regading data from database according to input params..");
		
		return productRepository.findAll(new Specification<Product>() {

			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (type != null) {
					predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("type"), "%" + type + "%")));
				}
				if (min_price != 0 && max_price != 0) {
					predicates.add(criteriaBuilder.between(root.get("price"), min_price, max_price));
				}
				if (min_price != 0 && max_price == 0) {
					predicates.add(criteriaBuilder.lessThan(root.get("price"), min_price));
				}
				if (min_price == 0 && max_price != 0) {
					predicates.add(criteriaBuilder.greaterThan(root.get("price"), max_price));
				}
				if (city != null) {
					predicates
							.add(criteriaBuilder.or(criteriaBuilder.like(root.get("store_address"), "%" + city + "%")));
				}
				if (properties != null) {
					predicates.add(
							criteriaBuilder.or(criteriaBuilder.like(root.get("properties"), "%" + properties + "%")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}


}