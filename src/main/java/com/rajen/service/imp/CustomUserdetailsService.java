package com.rajen.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rajen.entity.Users;
import com.rajen.repositories.UserRepository;



@Service
public class CustomUserdetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Users users=null;
		  Optional<Users> findByEmail = repository.findByEmail(username);
		  if(findByEmail.isPresent()) {
			 users = findByEmail.get();
		  }else {
			  throw new UsernameNotFoundException("user name not found ");
		  }
		return users;
	}

}
