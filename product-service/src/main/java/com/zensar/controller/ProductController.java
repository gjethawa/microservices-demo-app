package com.zensar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.dto.CouponDto;
import com.zensar.dto.ProductDto;
import com.zensar.rest.client.CouponClient;
import com.zensar.services.ProductService;

import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Product Resource") //Swagger API
@RequestMapping("/v1/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//@Autowired
	//private RestTemplate restTemplate;// no need if using openfeign
	
	@Autowired
	private CouponClient couponClient;
	
	//Insert Product
	@Operation(summary = "Insert Product", description = "Insert Product - accepts Product Object to be created with Coupon applied")
	@PostMapping(path="/",consumes = {MediaType.APPLICATION_JSON_VALUE})
	@Retry(name = "product-api" , fallbackMethod = "handleError") //circuit breaker - using resilience4j
	public @ApiResponse(description = "Product Object Newly Created") ResponseEntity<ProductDto> insertProduct(@Parameter(description = "New Product Object to be created") @RequestBody ProductDto productdto) {
		
		// here u had to copy Coupon from Coupon service. there are other methods in restTempate where u wont need Coupon class. u can explore it. e.g exchange method
		
	//	Coupon coupon = restTemplate.getForObject("http://COUPON-SERVICE/coupons/"+product.getCouponCode(), Coupon.class);  // using restTemplate
		ResponseEntity<List<CouponDto>> coupons = couponClient.getCoupon(productdto.getCouponCode()); // using openfeign
		System.out.println("coupons : "+coupons);
		Double couponDiscount = coupons.getBody().get(0).getDiscount();
		productdto.setPrice(productdto.getPrice() -couponDiscount );
		return new ResponseEntity<ProductDto> ( productService.insertProduct(productdto), HttpStatus.CREATED);
	}
	
	public ResponseEntity<ProductDto> handleError(Exception e) {
		System.out.println("Coupon Service is down!!!!");
		return new ResponseEntity<ProductDto> (new ProductDto(), HttpStatus.OK);
	}
	
	

}
