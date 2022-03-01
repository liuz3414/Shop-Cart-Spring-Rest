package com.syqu.shop.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "orders")
public class Orders{
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "cart_id")
	@NotNull
	@NotEmpty
	private long cart_id;
	
	@Column(name = "total_payment")
	@NotNull
	@NotEmpty
	private BigDecimal total_payment;
	
	@Column(name = "user_name")
	@NotNull
	@NotEmpty
	private String user_name;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getCartId() {
		return cart_id;
	}

	public void setCartId(long cart_id) {
		this.cart_id = cart_id;
	}
	
	public BigDecimal getTotalPayment() {
		return total_payment;
	}

	public void setTotalPayment(BigDecimal total_payment) {
		this.total_payment = total_payment;
	}
	
	public String getUserName() {
		return user_name;
	}

	public void setUserName(String user_name) {
		this.user_name = user_name;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id);
    }
}