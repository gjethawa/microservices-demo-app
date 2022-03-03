package com.zensar.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component // so that spring application makes a instance of this. 
public class MyFilter implements GlobalFilter{

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// GatewayFilterChain -> Filters chain. there can be multiple filters and a chain can be created of it
		// ServerWebExchange obj will have all the information about request
		System.out.println("Pre processing : "+exchange.getRequest()); 
		// Pre and Post will be executed FOR EVERY microservice request. e.g if product-service is calling coupon-service - this will be called twice. (once for each service)
		return chain.filter(exchange).then(Mono.fromRunnable(()->{
			System.out.println("Post porcessing : "+exchange.getResponse());
		}));
	}

}
