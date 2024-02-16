package com.rajen.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajen.entity.Cart;
import com.rajen.entity.Users;

public interface CartRepository  extends JpaRepository<Cart, String>{
   
	
	 
	  
	Optional<Cart>  findByUsers(Users user);
}
