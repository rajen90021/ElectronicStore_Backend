package com.rajen.service.imp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rajen.dtos.UserDto;
import com.rajen.dtos.pagableResponse;
import com.rajen.entity.Role;
import com.rajen.entity.Users;
import com.rajen.exception.resourcenotfoundexception;
import com.rajen.repositories.RoleRepository;
import com.rajen.repositories.UserRepository;
import com.rajen.service.UserService;



@Service
public class Userserviceimpl implements UserService {

	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${user.profile.image.path}")
	private   String path;
	
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private RoleRepository roleRepository;
	
	 String  normalid = "hdrbxvzsfujdhghg";
	
	@Override
	public UserDto createuser(UserDto userdto) {
		
		
	        	String randomid = UUID.randomUUID().toString();
		
	        	userdto.setUserid(randomid);
	        	userdto.setPassword(encoder.encode(userdto.getPassword()));

                 Users users = this.mapper.map(userdto,Users.class);
                 Role role=null;
        		 Optional<Role> findById = roleRepository.findById(normalid);
        		   if(findById.isPresent()) {
        			   
        			  role = findById.get();
        		   }else {
        			   throw new resourcenotfoundexception("role not found ");
        		   }
                  users.getRole().add(role);
                 
                 
                 Users saveuser = userrepository.save(users);
                 
		return this.mapper.map(saveuser, UserDto.class);
	}

	@Override
	public UserDto updateuser(UserDto userdto, String userid) {

		 Users users=null;
		
   Optional<Users> findById = userrepository.findById(userid);
		 if(findById.isPresent()) {
			users = findById.get();
			  
		  }else {
			  throw new resourcenotfoundexception("userid not found ");
		  }
		 
		 users.setName(userdto.getName());
//		 users.setEmail(userdto.getEmail());
		 users.setPassword(userdto.getPassword());
		 users.setGender(userdto.getGender());
		 users.setAbout(userdto.getAbout());
		 users.setUserimage(userdto.getUserimage());
		  
		 Users users2 = this.userrepository.save(users);
		
		return this.mapper.map(users2, UserDto.class);
	}

	@Override
	public void deleteuser(String userid) {
		 Users users=null;
			
		   Optional<Users> findById = userrepository.findById(userid);
				 if(findById.isPresent()) {
					users = findById.get();
					  
				  }else {
					  throw new resourcenotfoundexception("userid not found ");
				  }
				 
				 String userimage = users.getUserimage();
				 
				   String fullpath= path+userimage;
				   
				   Path path2 = Path.of(fullpath);
				   try {
					Files.delete(path2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						 
				 
				 userrepository.delete(users);
		
	}

	@Override
	public UserDto getbyid(String userid) {
		 Users users=null;
			
		   Optional<Users> findById = userrepository.findById(userid);
				 if(findById.isPresent()) {
					users = findById.get();
					  
				  }else {
					  throw new resourcenotfoundexception("userid not found ");
				  }
		return this.mapper.map(users, UserDto.class);
	}

	@Override
	public pagableResponse<UserDto> getalluser(int pagenumber ,int pagesize,String sortby,String sortdir) {

		  Sort sort = Sort.by(sortby);
		    
		    if (sortdir.equalsIgnoreCase("asc")) {
		        sort = sort.ascending();
		    } else {
		        sort = sort.descending();
		    }
		    
		
		Pageable pageable= PageRequest.of(pagenumber, pagesize,sort);
		          Page<Users> page = userrepository.findAll(pageable);
		          List<Users> list = page.getContent();
		       List<UserDto> listofuserdto = converttuserdto(list);
		       
		       pagableResponse<UserDto> response= new pagableResponse<>();
		       response.setContent(listofuserdto);
		       response.setPagenumber(page.getNumber());
		       response.setPageseize(page.getSize());
		       response.setTotalelement(page.getTotalElements());
		       response.setLastpage(page.isLast());
		       response.setTotalpages(page.getTotalPages());
		       

		return response;
	}

public List<UserDto> converttuserdto(List<Users> users){
	
	List<UserDto> listofuserdto= new ArrayList<>();
	
	for(Users userss :users) {
		
		UserDto userDto = this.mapper.map(userss, UserDto.class);
		 listofuserdto.add(userDto);
	}
	return listofuserdto;
	}
	@Override
	public UserDto getbyemail(String email) {

		
		 Users users =null;
      Optional<Users> findByEmail = this.userrepository.findByEmail(email);
      
      if(findByEmail.isPresent()) {
    	   users = findByEmail.get();
      }else {
    	  throw new resourcenotfoundexception("email not found ");
      }
      
		return mapper.map(users, UserDto.class);
	}

	@Override
	public List<UserDto> searchuser(String keyword) {

          List<Users> findByNameContaining = this.userrepository.findByNameContaining(keyword);
                     
             List<UserDto> list = converttuserdto(findByNameContaining);
             
		return list;
	}

	@Override
	public Optional<Users> findUsersByEmailOptional(String email) {
		// TODO Auto-generated method stub
		return userrepository.findByEmail(email);
	}

}
