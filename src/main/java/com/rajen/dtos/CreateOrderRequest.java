package com.rajen.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

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
public class CreateOrderRequest {

	@NotBlank(message = "Cart id is required !!")
    private String cartid;
    
	@NotBlank(message = "user id is required !!")
    private String userid;
	
	private String orderstatus="PENDING";
	
	
	private String paymentstatus="NOTPAID";
	

    @NotBlank(message = "Address is required !!")
	private String billingaddress;
	

    @NotBlank(message = "Phone number is required !!")
	private String billingphone;
    @NotBlank(message = "Billing name  is required !!")
	 private String billingName;
	
	
}
