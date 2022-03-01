package com.syqu.shop.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "cart")
public class Cart{
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	
	@Column(name = "total_price")
	@NotNull
	@NotEmpty
	private BigDecimal total_price;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getTotalPrice() {
		return total_price;
	}

	public void setTotalPrice(BigDecimal total_price) {
		this.total_price = total_price;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id);
    }
}