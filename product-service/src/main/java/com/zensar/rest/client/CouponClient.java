package com.zensar.rest.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zensar.dto.CouponDto;

//OPENFEIGN DEMO
@FeignClient("GATEWAY-SERVICE")
public interface CouponClient {
	
		@GetMapping("/v1/coupons/{couponCode}")
		ResponseEntity<List<CouponDto>> getCoupon(@PathVariable("couponCode") String counponCode);
}
