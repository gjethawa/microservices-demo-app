package com.zensar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.dto.CouponDto;
import com.zensar.services.CouponService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RefreshScope //WITHOUT RESTARTING YOUR SERVICE, YOU WANT TO UPDATE PROPERTIES? add this annotation and make a POST call to /actuator/refresh
@Tag(name = "Coupon Resource") //Swagger API
@RequestMapping("/v1/coupons")
public class CouponController {

	@Autowired
	private CouponService couponService;

	@Value("${test.properties}")
	private String testProperty; 
	
	@Value("${server.instance.name}")
	private String serverName;
	
	//Create Coupon
	@Operation(summary = "Create Coupon", description = "Create Coupon - accepts Coupon Object to be created") //swagger (documentation) API. Also see - @ApiResponse, @Parameter below
	@PostMapping(path="/",consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ApiResponse(description = "Coupon Object Newly Created") ResponseEntity<CouponDto> createCoupon(@Parameter(description = "New Coupon Object to be created") @RequestBody CouponDto coupondto) {
		return new ResponseEntity<CouponDto> (couponService.createCoupon(coupondto), HttpStatus.CREATED);
	}

	//Get Specific Coupon
	@Operation(summary = "Get Specific Coupons", description = "Gets Specific Coupons - accepts Coupon Code String") 
	@GetMapping(path="/{couponCode}",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<CouponDto>> getCoupon(@PathVariable("couponCode") String couponCode) {
		System.out.println("Server Name : "+serverName);
		System.out.println("Fetching Coupon with code : "+couponCode);
		return new ResponseEntity<List<CouponDto>> ( couponService.getCoupon(couponCode), HttpStatus.OK);
	}
	
	//Get all coupons
	@Operation(summary = "Get All Coupons", description = "Gets All Coupons") 
	@GetMapping(path="/",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<CouponDto>> getAllCoupons() {
		return new ResponseEntity<List<CouponDto>> ( couponService.getAllCoupons(), HttpStatus.OK);
	}

	//Get all active coupons
	@Operation(summary = "Get Active Coupons", description = "Gets All Active Coupons - Expiry after today") 
	@GetMapping(path="/active",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<CouponDto>> getCoupons() {
		return new ResponseEntity<List<CouponDto>> ( couponService.getActiveCoupons(), HttpStatus.OK);
	}
	
	//delete coupon by couponCode
	@Operation(summary = "Delete Coupon", description = "Deletes Specific Coupons by Coupon Code") 
	@DeleteMapping(path="/{couponCode}")
	public ResponseEntity<String> deleteCoupon(@PathVariable("couponCode") String couponCode) {
		couponService.deleteCoupon(couponCode);
		return new ResponseEntity<String> ("Coupon/s deleted successfuly : "+couponCode, HttpStatus.OK);
	}
	
	//update coupon
	@Operation(summary = "Update Coupon", description = "Updated Specific - accepts Coupon Object to be updated and Coupon Id") 
	@PutMapping(path = "/{couponId}",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<CouponDto> updateCoupon(@PathVariable("couponId") int couponId, @RequestBody CouponDto coupondto){
		return new ResponseEntity<CouponDto> (couponService.updateCoupon(couponId,coupondto), HttpStatus.OK);
	}
	
	// test property - set from config server
	@Hidden //will not show on Swagger
	@GetMapping("/profile")
	public String getTestProperty() {
		return testProperty;
	}
	

}
