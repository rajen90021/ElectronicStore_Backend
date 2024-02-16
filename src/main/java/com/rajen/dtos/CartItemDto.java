package com.rajen.dtos;


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
public class CartItemDto {
	
	private int cartitemid;
	
	
	private ProductDto product;
	
	
   private int quantity;
   
   
   private int totolprice;
   
  

}
