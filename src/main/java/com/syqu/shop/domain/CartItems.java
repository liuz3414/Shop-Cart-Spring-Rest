package com.syqu.shop.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "cartitems")
public class CartItems{
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "cart_id")
	@NotNull
	@NotEmpty
	private long cart_id;
	
	@Column(name = "production_id")
	@NotNull
	@NotEmpty
	private long production_id;
	
	@Column(name = "production_amount")
	@NotNull
	@NotEmpty
	private BigDecimal production_amount;
	
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

	public long getProductionId() {
		return production_id;
	}

	public void setProductionId(long production_id) {
		this.production_id = production_id;
	}

	public BigDecimal getProductionAmount() {
		return production_amount;
	}

	public void setProductionAmount(BigDecimal production_amount) {
		this.production_amount = production_amount;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id);
    }
}