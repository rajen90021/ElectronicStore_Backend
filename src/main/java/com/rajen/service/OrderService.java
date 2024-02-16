package com.rajen.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.rajen.dtos.CreateOrderRequest;
import com.rajen.dtos.OrderDto;
import com.rajen.dtos.pagableResponse;
import com.rajen.dtos.updatorderrequest;
import com.rajen.exception.badapirequest;

@Service
public interface OrderService {
	
	
	
//	creatre
	
	  public OrderDto createOrder(CreateOrderRequest orderDto) throws badapirequest;
	
	
	
//	remove 
	  public void removeOrder(String orderId);
	
//	get order of user 
	
	   public List<OrderDto> getOrdersOfUser(String userId);
//	getr order 

	
	
	   public pagableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

	   public OrderDto updateorder(String orderid, updatorderrequest request);
}
