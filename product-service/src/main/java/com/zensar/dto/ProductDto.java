package com.zensar.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

	private int productId;
	private String productName;
	private String description;
	private Double price;
	private String couponCode;

}
