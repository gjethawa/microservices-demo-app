package com.zensar.services;

import java.util.List;

import com.zensar.dto.CouponDto;

public interface CouponService {

	public CouponDto createCoupon(CouponDto couponDto);

	public List<CouponDto> getCoupon(String couponCode);
	
	public List<CouponDto> getAllCoupons();

	public List<CouponDto> getActiveCoupons();
	
	public void deleteCoupon(String couponCode);

	public CouponDto updateCoupon(int couponId, CouponDto coupondto);
	
}
