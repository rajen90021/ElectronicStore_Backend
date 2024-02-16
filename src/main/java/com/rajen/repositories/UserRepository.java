package com.rajen.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rajen.entity.Users;


@Repository
public interface UserRepository  extends JpaRepository<Users, String>{
	
	
    Optional<Users>	findByEmail(String email);
    
    List<Users> findByNameContaining(String keyword);

}
