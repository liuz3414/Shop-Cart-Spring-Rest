package com.syqu.shop.controller;

import com.syqu.shop.service.ShoppingOrderService;
import com.syqu.shop.domain.Orders;
import com.syqu.shop.domain.Product;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class OrderController{
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	private final ShoppingOrderService shoppingOrderService;
    
	private Map<Orders, Integer> list2map(List<Orders> lt) {
		
		
    	Map<Orders,Integer> map = new HashMap<Orders,Integer>();
    	
    	for(int i = 0;i < lt.size();i++){
    		map.put(lt.get(i), i);
    	}
    	
    	return map;
	}
	
    @Autowired
    public OrderController(ShoppingOrderService shoppingOrderService) {
    	this.shoppingOrderService = shoppingOrderService;
    }
    
    @GetMapping("/order")
    public String order(Model model){
    	
    	List<Orders> lt = this.shoppingOrderService.getOrders();
    	
    	model.addAttribute("orders", list2map(lt));
    	model.addAttribute("page", "order");
    	return "order";
    }
    
    @GetMapping("/order/{id}")
    public String ordering(@PathVariable("id") long id, Model model) {
    	
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        
    	this.shoppingOrderService.order(id, userName);
    	
    	List<Orders> lt = this.shoppingOrderService.getOrders();
    	
    	model.addAttribute("orders", list2map(lt));
    	model.addAttribute("page", "order");
    	return "redirect:/order";
    }
}