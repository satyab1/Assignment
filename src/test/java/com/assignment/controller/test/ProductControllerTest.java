package com.assignment.controller.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void getFilteredProductsWithType() throws Exception {

		mockMvc.perform(get("/product?type=phone").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].type", containsString("phone")));
	}

	@Test
	void getFilteredProductsWithPrice() throws Exception {

		mockMvc.perform(
				get("/product?type=phone&address=Svensson&properties=guld").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].type", containsString("phone")))
				.andExpect(jsonPath((String) "$[0].store_address", containsString("Nilsson allén Stockholm")));
	}

	@Test
	void getFilteredProductsWithProperties() throws Exception {

		mockMvc.perform(get("/product?type=phone&properties=guld").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].properties", containsString("guld")))
				.andExpect(jsonPath((String) "$[0].properties", containsString("guld")));
	}

	// simple test case to getting records of procuts
	@Test
	void getAllProducts() throws Exception {

		mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].type", containsString("phone")));
	}

}