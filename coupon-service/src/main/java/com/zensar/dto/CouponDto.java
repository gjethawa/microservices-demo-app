package com.zensar.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CouponDto {

	private int couponId;
	private String couponCode;
	private Double discount;
	
	@JsonProperty("expDate")
	@JsonFormat(pattern="dd/MM/yyyy")//to solve - Cannot deserialize value of type `java.util.Date` from String
	private Date expDate;
	
}
