package com.rajen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDto {

	
	

	private int orderitemid;

	
	
	private int quantity;
	
	
	private int totalprice;
	
	
	private ProductDto product;
	
	
}
