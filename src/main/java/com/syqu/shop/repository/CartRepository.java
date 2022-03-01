package com.syqu.shop.repository;

import com.syqu.shop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
    @Query("SELECT coalesce(max(ch.id), 0) FROM Cart ch")
    long getMaxId();
}
