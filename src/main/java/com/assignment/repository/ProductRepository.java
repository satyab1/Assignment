package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.assignment.model.Product;

public interface ProductRepository extends CrudRepository<Product, String>, JpaSpecificationExecutor<Product> {
}
