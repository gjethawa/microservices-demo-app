package com.zensar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.dto.ProductDto;
import com.zensar.entity.Product;
import com.zensar.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductDto insertProduct(ProductDto productdto) {
		return mapToDto (productRepository.save(mapToEntity(productdto)));
	}

	private Product mapToEntity (ProductDto productdto) {
		Product product = new Product();
		product.setProductName(productdto.getProductName());
		product.setDescription(productdto.getDescription());
		product.setPrice(productdto.getPrice());
		product.setCouponCode(productdto.getCouponCode());
		return product;
	}
	
	private ProductDto mapToDto (Product product) {
		ProductDto productdto = new ProductDto();
		productdto.setProductId(product.getProductId());
		productdto.setProductName(product.getProductName());
		productdto.setDescription(product.getDescription());
		productdto.setPrice(product.getPrice());
		productdto.setCouponCode(product.getCouponCode());
		return productdto;
	}
}
