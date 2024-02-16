package com.rajen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rajen.dtos.UserDto;
import com.rajen.dtos.pagableResponse;
import com.rajen.entity.Users;

@Service
public interface UserService {

	//create
	   UserDto createuser(UserDto userdto);
	
	   //update 
	   UserDto updateuser(UserDto userdto, String userid);
	   
	   //delete
	   
	   void deleteuser(String userid);
	   
	   //get by id
	   
	   UserDto getbyid(String userid);
	   
	   //get all user
	   
	   pagableResponse<UserDto> getalluser(int pagenumber ,int pagesize, String sortby,String sortdir);
	   //get by email
	   UserDto getbyemail(String email);
	   
	   //search user
	   List<UserDto> searchuser(String keyword);
	   
	   
	   Optional<Users> findUsersByEmailOptional(String email);
	   
}
