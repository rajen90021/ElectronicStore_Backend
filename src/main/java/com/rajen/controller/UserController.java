package com.rajen.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rajen.dtos.UserDto;
import com.rajen.dtos.apiresponse;
import com.rajen.dtos.imageresponse;
import com.rajen.dtos.pagableResponse;
import com.rajen.service.UserService;
import com.rajen.service.imp.fileserviceimp;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;
	
	
	@Value("${user.profile.image.path}")
	private   String path;
	@Autowired
	private fileserviceimp fileservice;
	
	
	//create 
	@PostMapping
	public ResponseEntity<UserDto> createuser( @Valid @RequestBody UserDto dto){
		
		UserDto createuser = service.createuser(dto);
		
		return new ResponseEntity<UserDto>(createuser,HttpStatus.CREATED);
	}
	
	//update 
	
	@PutMapping("/{userid}")
	public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto dto , @PathVariable("userid") String userid){
		UserDto updateuser = service.updateuser(dto, userid);
		return new ResponseEntity<UserDto>(updateuser,HttpStatus.OK);
		
	}
	
	//delete 
	@DeleteMapping("/{userid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<apiresponse> deleteuser(@PathVariable("userid") String userid){
		
		this.service.deleteuser(userid);
		
		
		    apiresponse apiresponse= new apiresponse();
		    
		    apiresponse.setMessage("user delete sucessfully ");
		    
		    apiresponse.setSucess(true);
		    
		    apiresponse.setStatus(HttpStatus.OK);
		
		return new ResponseEntity<apiresponse>(apiresponse,HttpStatus.OK);
	}
	
	
	//get all 
	@GetMapping
	public ResponseEntity<pagableResponse<UserDto>> getalluser(
			
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			@RequestParam(value = "pagesize",defaultValue = "10",required = false) int pagesize,
			@RequestParam(value = "sortby",defaultValue = "name",required = false) String sortby,
			@RequestParam(value = "sortdir",defaultValue = "asc",required = false) String sortdir
			){
		pagableResponse<UserDto> getalluser = this.service.getalluser(pagenumber,pagesize,sortby,sortdir);
		return new ResponseEntity<pagableResponse<UserDto>>(getalluser,HttpStatus.OK);
	}
	
	
//	get single user
	
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getsingleuser(@PathVariable("userid") String userid){
		
		UserDto getbyid = service.getbyid(userid);
		return new ResponseEntity<UserDto>(getbyid,HttpStatus.OK);
	}
	
//	get  by email
	
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getbyemail(@PathVariable("email") String email){
		
		UserDto getbyemail = service.getbyemail(email);
		return new ResponseEntity<UserDto>(getbyemail ,HttpStatus.OK);
	}
	
	
//	get search 
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> searchuser(@PathVariable("keyword") String keyword){
		
		List<UserDto> searchuser = service.searchuser(keyword);
		
		return new ResponseEntity<List<UserDto>>(searchuser,HttpStatus.OK);
	}
	
//	upload image 
	
	@PostMapping("/image/{userid}")
	public ResponseEntity<imageresponse> uploadimage(
			@RequestParam("image") MultipartFile image, @PathVariable("userid") String userid
			) throws IOException{
		
		           String imagenanme = fileservice.uploadfile(image, path);
		           UserDto userDto = service.getbyid(userid);
		           userDto.setUserimage(imagenanme);
		           UserDto updateuser = service.updateuser(userDto, userid);
		           
		           imageresponse imageresponse= new imageresponse();
		           imageresponse.setImagename(imagenanme);
		           imageresponse.setMessage("image uploaded successfully");
		           imageresponse.setStatus(HttpStatus.CREATED);
		           imageresponse.setSucess(true);
		           
		           
		
				return new ResponseEntity<imageresponse>(imageresponse,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/image/{userid}")
	public void serveimage(@PathVariable("userid") String userid,HttpServletResponse response) throws IOException {
		
		UserDto userUserByid = this.service.getbyid(userid);	
		
		String imageName = userUserByid.getUserimage();
//		InputStream serveimage = this.fileserviceimp.serveimage(uploadImagePath, imageName);
		  InputStream serveimage = this.fileservice.serveimage(path, imageName);
		
		  response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	        StreamUtils.copy(serveimage, response.getOutputStream());
	
	}
	
	
	
	
	
	
	
	
}
