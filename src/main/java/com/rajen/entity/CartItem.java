package com.rajen.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CartItem {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartitemid;
	
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	
   private int quantity;
   
   
   private int totolprice;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "cart_id")
   private  Cart cart;
   
}
