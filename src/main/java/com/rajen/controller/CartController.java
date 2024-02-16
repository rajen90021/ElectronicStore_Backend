package com.rajen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajen.dtos.CartDto;
import com.rajen.dtos.addItemToCartRequest;
import com.rajen.dtos.apiresponse;
import com.rajen.service.imp.CartServiceImp;

@RestController
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	private  CartServiceImp cartServiceImp;
	
	
	
	@PostMapping("/{userid}")
	public ResponseEntity<CartDto> additemtocard(@RequestBody addItemToCartRequest request, @PathVariable("userid") String userid){
		
		
		  CartDto addproducttocart = cartServiceImp.addproducttocart(userid, request);
		  return new ResponseEntity<CartDto>(addproducttocart,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{userid}/items/{itemid}")
	public ResponseEntity<apiresponse> removeitemfromcart(@PathVariable("userid") String userid,@PathVariable("itemid") int itemid){
		 cartServiceImp.removeitemfromcart(userid, itemid);
		 apiresponse apiresponse= new apiresponse();
		
	  apiresponse.setMessage("item remove succfully ");
	  apiresponse.setStatus(HttpStatus.OK);
	  apiresponse.setSucess(true);
	  return new ResponseEntity<apiresponse>(apiresponse,HttpStatus.OK);
	}

	
	
	
	@DeleteMapping("/{userid}")
	public ResponseEntity<apiresponse> clearcart(@PathVariable("userid") String userid){
		 cartServiceImp.clearcart(userid);
		 apiresponse apiresponse= new apiresponse();
		
	  apiresponse.setMessage("item remove succfully ");
	  apiresponse.setStatus(HttpStatus.OK);
	  apiresponse.setSucess(true);
	  return new ResponseEntity<apiresponse>(apiresponse,HttpStatus.OK);
	}
	
	
	@GetMapping("/{userid}")
	public ResponseEntity<CartDto> getcart( @PathVariable("userid") String userid){
		
		
		  CartDto addproducttocart = cartServiceImp.getcardbyuser(userid);
		  return new ResponseEntity<CartDto>(addproducttocart,HttpStatus.CREATED);
	}
}
