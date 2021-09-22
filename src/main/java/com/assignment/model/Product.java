package com.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

//defining class name as Table name Product

@Table
public class Product {

	// defining type as column name
	@Column
	private String type;

	// defining properties as column name
	@Column
	private String properties;

	// defining price as column name
	@Column
	private double price;

	// defining store_address as column name
	@Id
	@Column
	private String store_address;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStore_address() {
		return store_address;
	}

	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}
}