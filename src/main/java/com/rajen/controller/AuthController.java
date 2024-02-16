package com.rajen.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.rajen.Security.JwtHelper;
import com.rajen.dtos.JwtRequest;
import com.rajen.dtos.JwtResponse;
import com.rajen.dtos.UserDto;
import com.rajen.entity.Users;
import com.rajen.exception.BadCredentialsExceptionn;
import com.rajen.exception.badapirequest;
import com.rajen.service.imp.CustomUserdetailsService;
import com.rajen.service.imp.Userserviceimpl;



@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	
	@Autowired
    private CustomUserdetailsService customUserdetailsService;
	
	 @Autowired
	    private UserDetailsService userDetailsService;

	    @Autowired
	    private AuthenticationManager manager;


	    @Autowired
	    private JwtHelper helper;
	
	@Autowired
	private ModelMapper mapper;
	 @Autowired
	  private Userserviceimpl imp;
	 
	 
	 private String newPassword="fssfseerhyrdhdfbsvsfefetfaf";
	 
	 private String googleClientId="118293484707-eesonjlc1p6g6f42n7adf0usou62rq0t.apps.googleusercontent.com";
	
	@GetMapping("/current")
	public ResponseEntity<UserDto> current(Principal principal){
	   String name = principal.getName();
	      UserDto userDto = mapper.map(customUserdetailsService.loadUserByUsername(name), UserDto.class);
	      
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	
	
	   @PostMapping("/login")
	    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

	        this.doAuthenticate(request.getEmail(), request.getPassword());


	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        String token = this.helper.generateToken(userDetails);
	        
	          UserDto userDto = mapper.map(userDetails, UserDto.class);
	          

	        JwtResponse response = JwtResponse.builder()
	                .jwtToken(token)
	                .dto(userDto).build();
	                
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    private void doAuthenticate(String email, String password) {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	            manager.authenticate(authentication);


	        } catch (BadCredentialsException e) {
	            throw new BadCredentialsExceptionn(" Invalid Username or Password  !!");
	        }

	    }
//login with google api
	    
	    
	    @PostMapping("/google")
	    public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody Map<String, Object> data) throws IOException, badapirequest {


	        //get the id token from request
	        String idToken = data.get("idToken").toString();

	        NetHttpTransport netHttpTransport = new NetHttpTransport();

	        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

	        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClientId));


	        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), idToken);


	        GoogleIdToken.Payload payload = googleIdToken.getPayload();

	       

	        String email = payload.getEmail();

	        Users user = null;

	        user = imp.findUsersByEmailOptional(email).orElse(null);

	        if (user == null) {
	            //create new user
	            user = this.saveUser(email, data.get("name").toString(), data.get("photoUrl").toString());
	        }
	        ResponseEntity<JwtResponse> jwtResponseResponseEntity = this.login(JwtRequest.builder().email(user.getEmail()).password(newPassword).build());
	        return jwtResponseResponseEntity;


	    }
	    private Users saveUser(String email, String name, String photoUrl) {

	        UserDto newUser = UserDto.builder()
	                .name(name)
	                .email(email)
	                .password(newPassword)
	                .userimage(photoUrl)
	                .role(new HashSet<>())
	                .build();

	        
//	                UserDto newUser= new UserDto();
//	                
//	                newUser.setName(name);
//	                
//	                newUser.setEmail(email);
//	                newUser.setPassword(newPassword);
//	                newUser.setImageName(photoUrl);
//	                newUser.setRole(new HashSet<>());
	                
	        UserDto user = imp.createuser(newUser);

	        return this.mapper.map(user, Users.class);


	    }


	
}
