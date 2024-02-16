package com.rajen.service.imp;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajen.dtos.CartDto;
import com.rajen.dtos.addItemToCartRequest;
import com.rajen.entity.Cart;
import com.rajen.entity.CartItem;
import com.rajen.entity.Product;
import com.rajen.entity.Users;
import com.rajen.exception.badapirequest;
import com.rajen.exception.resourcenotfoundexception;
import com.rajen.repositories.CaritemRepository;
import com.rajen.repositories.CartRepository;
import com.rajen.repositories.ProdcutRepository;
import com.rajen.repositories.UserRepository;
import com.rajen.service.CartService;


@Service
public class CartServiceImp implements CartService {
	
	@Autowired
	private  ProdcutRepository prodcutRepository;
	
	@Autowired
	 private  ModelMapper mapper;
	  
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CaritemRepository caritemRepository;

	@Override
	public CartDto addproducttocart(String userid, addItemToCartRequest request) {
		
		  Users users = null;
		    Product product = null;

		    int quantity = request.getQuantity();
		    String productId = request.getProductid();

		    if (quantity <= 0) {
		        throw new badapirequest("Product quantity is not valid");
		    }

		    Optional<Users> findById = userRepository.findById(userid);
		    if (findById.isPresent()) {
		        users = findById.get();
		    } else {
		        throw new resourcenotfoundexception("User id not found");
		    }

		    Optional<Product> findById2 = prodcutRepository.findById(productId);
		    if (findById2.isPresent()) {
		        product = findById2.get();
		    } else {
		        throw new resourcenotfoundexception("Product id not found");
		    }

		    Cart cart = null;
		    try {
		        Optional<Cart> findByUsers = cartRepository.findByUsers(users);
		        if (findByUsers.isPresent()) {
		            cart = findByUsers.get();
		        } else {
		            cart = new Cart(); 
		            cart.setCartid(UUID.randomUUID().toString());// Initialize cart with a new instance
		            cart.setCreatedat(new Date());
		        }
		    } catch (NoSuchElementException e) {
		        cart = new Cart();
		        cart.setCartid(UUID.randomUUID().toString());// Initialize cart with a new instance
		        cart.setCreatedat(new Date());
		    }

		    // Perform the operation
		    List<CartItem> cartItems = cart.getItems();
		    boolean updated = false;

		    for (CartItem cartItem : cartItems) {
		        if (cartItem.getProduct().equals(product)) {
		            cartItem.setQuantity(quantity);
		            cartItem.setTotolprice(quantity * product.getDiscount());
		            updated = true;
		            break;
		        }
		    }

		    if (!updated) {
		        CartItem cartItem = new CartItem();
		        cartItem.setQuantity(quantity);
		        cartItem.setTotolprice(quantity * product.getDiscount());
		        cartItem.setCart(cart);
		        cartItem.setProduct(product);
		        cart.getItems().add(cartItem);  // Update the cart instance's items
		    }

		    cart.setUsers(users);
		    Cart updatedCart = cartRepository.save(cart);

		    return mapper.map(updatedCart, CartDto.class);
	}

	@Override
	public void removeitemfromcart(String userid, int cartitemid) {
		
		CartItem cartItem2= null;
		Optional<CartItem> findById = caritemRepository.findById(cartitemid);
		if(findById.isPresent()) {
		cartItem2 = findById.get();
		}else {
			throw new resourcenotfoundexception("cart item not found ");
		}
		
		 caritemRepository.delete(cartItem2);
	}
	
	public void clearcart(String userid) {
		 Users users=null; 
		 Cart cart=null;
		
		Optional<Users> findById = userRepository.findById(userid);
		   if(findById.isPresent()) {
			    users = findById.get();
		   }else {
			   throw new resourcenotfoundexception("user id not found ");
		   }
		   
		   Optional<Cart> findByUsers = cartRepository.findByUsers(users);
		   if(findByUsers.isPresent()) {
			  cart = findByUsers.get();
		   }else {
			   throw new resourcenotfoundexception("cart of given user not found ");
		   }
		   List<CartItem> items = cart.getItems();
		   items.clear();
		   cartRepository.save(cart);
		   
		
	}
	
	public CartDto getcardbyuser(String userid) {
		 Users users=null; 
		 Cart cart=null;
			
		Optional<Users> findById = userRepository.findById(userid);
		   if(findById.isPresent()) {
			    users = findById.get();
		   }else {
			   throw new resourcenotfoundexception("user id not found ");
		   }
		   Optional<Cart> findByUsers = cartRepository.findByUsers(users);
		   if(findByUsers.isPresent()) {
			  cart = findByUsers.get();
		   }else {
			   throw new resourcenotfoundexception("cart of given user not found ");
		   }
		   
		   
		   return mapper.map(cart, CartDto.class);
	}
 
}
