package com.syqu.shop.service.impl;

import com.syqu.shop.service.ShoppingCartService;
import com.syqu.shop.service.ShoppingOrderService;
import com.syqu.shop.domain.Orders;
import com.syqu.shop.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
	
	private final ShoppingCartService shoppingCartservice;
	private final OrderRepository orderRepository;
	
	
	public ShoppingOrderServiceImpl(ShoppingCartService shoppingCartService, OrderRepository orderRepository){
		this.shoppingCartservice = shoppingCartService;
		this.orderRepository = orderRepository;
	}
	
	@Override
	public void order(long cartId, String userName){
		
		BigDecimal total_payment = this.shoppingCartservice.totalPrice();
		
		Orders order = new Orders();
		order.setCartId(cartId);
		order.setUserName(userName);
		order.setTotalPayment(total_payment);
		
		this.orderRepository.save(order);
		this.shoppingCartservice.clearProducts();
	}

	@Override
	public List<Orders> getOrders() {
		// TODO Auto-generated method stub
		return this.orderRepository.findAll();
	}

}