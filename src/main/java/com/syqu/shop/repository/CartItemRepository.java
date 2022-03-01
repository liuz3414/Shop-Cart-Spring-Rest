package com.syqu.shop.repository;

import com.syqu.shop.domain.CartItems;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
	//@Query("DELETE FROM cartitems WHERE cart_id = ?1")
	//void deleteByCartId(long cart_id);
}
