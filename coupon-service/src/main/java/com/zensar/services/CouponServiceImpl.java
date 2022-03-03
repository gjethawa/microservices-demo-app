package com.zensar.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zensar.exception.ResourceNotFoundException;
import com.zensar.dto.CouponDto;
import com.zensar.entity.Coupon;
import com.zensar.repository.CouponRepository;

@Service
public class CouponServiceImpl implements CouponService {
	
	@Autowired
	private CouponRepository couponRepository;

	@Override
	public CouponDto createCoupon(CouponDto coupondto) {
		Coupon couponSaved =  couponRepository.save(mapToEntity(coupondto));
		return mapToDto(couponSaved);
	}
	
	@Override
	public List<CouponDto> getCoupon(String couponCode) {
		return mapToDtos(couponRepository.findByCouponCode(couponCode));
	}


	@Override
	public List<CouponDto> getAllCoupons() {
		return mapToDtos(couponRepository.findAll());
	}


	@Override
	public List<CouponDto> getActiveCoupons() {
		return mapToDtos(couponRepository.getActiveCoupons());
	}
	
	@Override
	public CouponDto updateCoupon(int couponId, CouponDto coupondto) {
		Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new ResourceNotFoundException("couponId", "id", couponId));
		Coupon couponUpdated = mapToEntity(coupondto);
		couponUpdated.setCouponId(coupon.getCouponId());
		couponUpdated = couponRepository.save(couponUpdated);
		return (mapToDto(couponUpdated));

	}
	
	private List<CouponDto> mapToDtos (List<Coupon> coupons){
		List<CouponDto> couponDtos = new ArrayList<>();
		for (Coupon c:coupons) {
			couponDtos.add(mapToDto(c));
		}
		return couponDtos;
	}
	
	private Coupon mapToEntity(CouponDto coupondto) {
		Coupon couponEntity = new Coupon();
		couponEntity.setCouponCode(coupondto.getCouponCode());
		couponEntity.setDiscount(coupondto.getDiscount());
		couponEntity.setExpDate(coupondto.getExpDate());
		return couponEntity;
	}
	
	private CouponDto mapToDto(Coupon coupon) {
		CouponDto coupondto = new CouponDto();
		coupondto.setCouponId(coupon.getCouponId());
		coupondto.setCouponCode(coupon.getCouponCode());
		coupondto.setDiscount(coupon.getDiscount());
		coupondto.setExpDate(coupon.getExpDate());
		return coupondto;
	}


	@Override
	public void deleteCoupon(String couponCode) {
		List<Coupon> coupons =  couponRepository.findByCouponCode(couponCode);
		if(!CollectionUtils.isEmpty(coupons)) {
			couponRepository.deleteAll(coupons);
		}else {
			throw new RuntimeException("Invalid Coupon Code. No coupons found with code : "+couponCode);
		}
	}

}
