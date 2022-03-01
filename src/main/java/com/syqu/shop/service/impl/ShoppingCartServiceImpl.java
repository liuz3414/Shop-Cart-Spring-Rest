package com.syqu.shop.service.impl;

import com.syqu.shop.domain.Product;
import com.syqu.shop.domain.Cart;
import com.syqu.shop.domain.CartItems;
import com.syqu.shop.service.ShoppingCartService;
import com.syqu.shop.repository.CartRepository;
import com.syqu.shop.repository.CartItemRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/*
Thanks to Dusan!
www.github.com/reljicd/spring-boot-shopping-cart
 */

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private Map<Product, Integer> cart = new LinkedHashMap<>();
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private long cart_id;
    
    public ShoppingCartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
    	this.cartRepository = cartRepository;
    	this.cartItemRepository = cartItemRepository;
    	cart_id = 0;
    }
    
    
    private void syncCartDb(){
    	double sum = 0;
    	
    	//Calculating sum of cart
    	for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            sum += entry.getKey().getPrice().doubleValue() * entry.getValue();
        }
    	
    	//Update CartRepository
    	Cart found = new Cart();
    	found.setTotalPrice(BigDecimal.valueOf(sum));
    	
    	//cartRepository.deleteById(cart_id);
    	cart_id = cartRepository.save(found).getId();
    	System.out.print("_------------------------_" + found + "\n");
    	
    	//Update CartItemRepository
    	CartItems item = new CartItems();
    	for (Map.Entry<Product, Integer> entry : cart.entrySet()) 
    	{
    		item.setCartId(cart_id);
    		item.setProductionId(entry.getKey().getId());
    		item.setProductionAmount(BigDecimal.valueOf(entry.getValue()));
    		item = cartItemRepository.save(item);
    		item = new CartItems();
    	}
    	
    }

    @Override
    public void addProduct(Product product) {
        if (cart.containsKey(product)){
            cart.replace(product, cart.get(product) + 1);
        }else{
            cart.put(product, 1);
        }
        syncCartDb();
    }

    @Override
    public void removeProduct(Product product) {
        if (cart.containsKey(product)) {
            if (cart.get(product) > 1)
                cart.replace(product, cart.get(product) - 1);
            else if (cart.get(product) == 1) {
                cart.remove(product);
            }
        }
        syncCartDb();
    }

    @Override
    public void clearProducts() {
    	cartRepository.deleteAll();
    	cartItemRepository.deleteAll();
        cart.clear();
    }

    @Override
    public Map<Product, Integer> productsInCart() {
        return Collections.unmodifiableMap(cart);
    }

    @Override
    public BigDecimal totalPrice() {
        return cart.entrySet().stream()
                .map(k -> k.getKey().getPrice().multiply(BigDecimal.valueOf(k.getValue()))).sorted()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public void cartCheckout() {
        cart.clear();
        // Normally there would be payment etc.
    }


	@Override
	public Object getMaxId() {
		// TODO Auto-generated method stub
		return this.cartRepository.getMaxId();
	}


}
