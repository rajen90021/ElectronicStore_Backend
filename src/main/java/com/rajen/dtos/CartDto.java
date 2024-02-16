package com.rajen.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rajen.entity.Cart;
import com.rajen.entity.CartItem;
import com.rajen.entity.Product;

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
public class CartDto {
	
	private String  cartid;

    private Date createdat;
    
    

    private UserDto users;
    
     private List<CartItemDto> items= new ArrayList<>();
}
