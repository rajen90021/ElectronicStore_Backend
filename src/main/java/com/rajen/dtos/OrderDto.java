package com.rajen.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@ToString
@Builder
public class OrderDto {

	
	
	private String orderid;
	
	
	private String orderstatus="PENDING";
	
	
	private String paymentstatus="NOTPAID";
	
	
	private int orderamount;
	
	private String billingaddress;
	
	
	private String billingphone;
	
	 private String billingName;
	
	private  Date orderDate=new Date();
	
	private Date deliverddate;
	
	
//	private Users users;
		private List<OrderItemDto> orderitems= new ArrayList<>();
	
	
	
}
