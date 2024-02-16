package com.rajen.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

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

import com.rajen.dtos.CategoryDto;
import com.rajen.dtos.ProductDto;
import com.rajen.dtos.apiresponse;
import com.rajen.dtos.imageresponse;
import com.rajen.dtos.pagableResponse;
import com.rajen.service.imp.fileserviceimp;
import com.rajen.service.imp.productServiceImp;

@RestController
@RequestMapping("/product")
public class productController {

	@Autowired
	private productServiceImp productService;
	
	
	@Autowired
	private fileserviceimp fileservice;
	
	@Value("${product.profile.image.path}")
	private String imagepath;
	
	
	
//	   create 
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto){
		
		   ProductDto create = productService.create(dto);
		   
		   return  new ResponseEntity<ProductDto>(create,HttpStatus.CREATED);
	}
	
	
//	update 
	
	@PutMapping("/{productid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDto> updfate(@RequestBody ProductDto dto,@PathVariable("productid") String  productid){
		
		   ProductDto create = productService.update(dto,productid);
		   
		   return  new ResponseEntity<ProductDto>(create,HttpStatus.CREATED);
	}
	
//	delete 
	
	@DeleteMapping("/{productid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<apiresponse> delete(@PathVariable("productid") String  productid){
		
		  productService.delete(productid);
		   apiresponse apiresponse= new apiresponse();
		   
		   apiresponse.setMessage("product delete sucess ");
		   apiresponse.setSucess(true);
		   apiresponse.setStatus(HttpStatus.OK);
		   
		   return  new ResponseEntity<apiresponse>(apiresponse,HttpStatus.OK);
	}
	
//	get sngle 
	
	@GetMapping("/{productid}")
	public ResponseEntity<ProductDto> getsingle(@PathVariable("productid") String  productid){
		
		  ProductDto productDto = productService.getbyid(productid);
		   apiresponse apiresponse= new apiresponse();
		   
		   
		   
		   return  new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
	}
//	gelll all 
	
	@GetMapping
	public ResponseEntity<pagableResponse<ProductDto>> getall(
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			
			@RequestParam(value = "pagesize",defaultValue = "10",required = false) int pagesize,
			
			@RequestParam(value = "sortby",defaultValue = "title",required = false) String sortby,
			
			
			@RequestParam(value = "sortdir",defaultValue = "asc",required = false) String sortdir
			
			){
		pagableResponse<ProductDto> pagableResponse = productService.getall(pagenumber, pagesize, sortby, sortdir);
		
		  return new ResponseEntity<pagableResponse<ProductDto>>(pagableResponse,HttpStatus.OK);
	}
	
	
//	gal all live 
	
	
	
	@GetMapping("/live")
	public ResponseEntity<pagableResponse<ProductDto>> getalllive(
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			
			@RequestParam(value = "pagesize",defaultValue = "10",required = false) int pagesize,
			
			@RequestParam(value = "sortby",defaultValue = "title",required = false) String sortby,
			
			
			@RequestParam(value = "sortdir",defaultValue = "asc",required = false) String sortdir
			
			){
		pagableResponse<ProductDto> pagableResponse = productService.getalllive(pagenumber, pagesize, sortby, sortdir);
		
		  return new ResponseEntity<pagableResponse<ProductDto>>(pagableResponse,HttpStatus.OK);
	}
	
//	search all 
	@GetMapping("/search/{keyword}")
	public ResponseEntity<pagableResponse<ProductDto>> search(
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			
			@RequestParam(value = "pagesize",defaultValue = "10",required = false) int pagesize,
			
			@RequestParam(value = "sortby",defaultValue = "title",required = false) String sortby,
			
			
			@RequestParam(value = "sortdir",defaultValue = "asc",required = false) String sortdir,
			@PathVariable("keyword") String keyword
			
			){
		pagableResponse<ProductDto> pagableResponse = productService.searchproduct( keyword,pagenumber, pagesize, sortby, sortdir);
		
		  return new ResponseEntity<pagableResponse<ProductDto>>(pagableResponse,HttpStatus.OK);
	}
	
	
	@PostMapping("/image/{productid}")
	public ResponseEntity<imageresponse> uploadimageproduct(@PathVariable("productid")String productid,
			
			@RequestParam("productimage") MultipartFile image
			) throws IOException{
		String uploadfile = fileservice.uploadfile(image, imagepath);
		ProductDto getbyid = productService.getbyid(productid);
		getbyid.setProductimage(uploadfile);
		ProductDto update = productService.update(getbyid, productid);
		
		imageresponse imageresponse= new imageresponse();
		imageresponse.setImagename(uploadfile);
		imageresponse.setMessage("file uploaded sucessfully");
		imageresponse.setStatus(HttpStatus.CREATED);
		imageresponse.setSucess(true);		
		
		return new ResponseEntity<imageresponse>(imageresponse,HttpStatus.CREATED);
		  
	}
	
	
	@GetMapping("/image/{productid}")
	public void servecategoryimage(@PathVariable("productid") String productid,HttpServletResponse response) throws IOException {
		
 ProductDto productDto = productService.getbyid(productid);
		String coverimage = productDto.getProductimage();
	InputStream serveimage = fileservice.serveimage(imagepath, coverimage);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(serveimage, response.getOutputStream());
		
	}
}
	
	
	
	
