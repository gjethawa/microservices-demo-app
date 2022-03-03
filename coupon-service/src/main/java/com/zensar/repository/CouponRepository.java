package com.zensar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zensar.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	List<Coupon> findByCouponCode(String couponCode);
	
	@Query(value="FROM Coupon c WHERE c.expDate >= CURRENT_DATE")
	List<Coupon> getActiveCoupons();
}
