package com.assignment.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.assignment.model.Product;

@Component
public class ReadCSvFile {

	private Function<String, Product> mapToItem = (line) -> {
		String[] p = line.split(",");
		Product item = new Product();
		item.setType(p[0]);
		item.setProperties(p[1]);
		item.setPrice(Double.parseDouble(p[2]));
		if (p[3] != null && p[3].trim().length() > 0 || p[4] != null && p[4].trim().length() > 0) {
			item.setStore_address(p[3] + p[4]);
		}
		return item;
	};

	/*
	 * Reading csv file column wise and setting to Product model Used UTF-8 because
	 * CSV file having German umlaut (ä, ö, ü)
	 */

	public List<Product> processInputFile(String inputFilePath) {
		List<Product> inputList = new ArrayList<Product>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath), "UTF-8"));
			inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
			br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return inputList;
	}

}
