package com.syqu.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.syqu.shop.domain.Orders;


@Service
public interface ShoppingOrderService {
	List<Orders> getOrders();
	void order(long cartId, String userName);
}