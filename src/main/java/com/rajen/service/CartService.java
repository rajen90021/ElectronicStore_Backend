package com.rajen.service;

import org.springframework.stereotype.Service;

import com.rajen.dtos.CartDto;
import com.rajen.dtos.addItemToCartRequest;

@Service
public interface CartService {
	
	
	
	public CartDto  addproducttocart(String userid,addItemToCartRequest request);
	
	
	public void removeitemfromcart(String userid,int cartitemid);
	
	public void clearcart(String userid);
	public CartDto getcardbyuser(String userid);

}
