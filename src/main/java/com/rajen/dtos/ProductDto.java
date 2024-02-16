package com.rajen.dtos;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	
	
	
	private String id;
	
	
	private String title;
	
	
	
	private String discription;
	
	private int price;
	
	
	private int discount;
	
	
	
	private int quantity;
	
	
	private Date addeddate;
	
	
	private boolean live;
	
	
	private boolean stock;
	
	private String productimage;
	private CategoryDto category;
}
