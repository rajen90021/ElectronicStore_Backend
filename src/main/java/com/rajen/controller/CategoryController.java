package com.rajen.controller;

import java.io.IOException;
import java.io.InputStream;

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

import com.rajen.dtos.CategoryDto;
import com.rajen.dtos.ProductDto;
import com.rajen.dtos.apiresponse;
import com.rajen.dtos.imageresponse;
import com.rajen.dtos.pagableResponse;
import com.rajen.repositories.ProdcutRepository;
import com.rajen.service.imp.CategoryServiceimp;
import com.rajen.service.imp.fileserviceimp;
import com.rajen.service.imp.productServiceImp;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	   private CategoryServiceimp categoryService;
	@Autowired
	private fileserviceimp fileservice;
	
	@Value("${category.profile.image.path}")
	private String path;
	
	@Autowired
	private productServiceImp productimp;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){
		
		
		    CategoryDto create = categoryService.create(categoryDto);
		    return new ResponseEntity<CategoryDto>(create,HttpStatus.CREATED);
	}
	
	@PutMapping("/{catid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> upodate(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("catid") String catid){
		
		CategoryDto updatecategroy = categoryService.updatecategroy(categoryDto, catid);
		return new ResponseEntity<CategoryDto>(updatecategroy,HttpStatus.OK);
	}
	
	@DeleteMapping("/{catid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<apiresponse> delete(@PathVariable("catid") String catid){
		categoryService.delete(catid);
		apiresponse apiresponsee=new apiresponse();
		apiresponsee.setMessage("categories delete sucessfully");
		apiresponsee.setStatus(HttpStatus.OK);
		apiresponsee.setSucess(true);
		return new ResponseEntity<apiresponse>(apiresponsee,HttpStatus.OK);
		
		
	}
//	get single 
	
	@GetMapping("/{catid}")
	public  ResponseEntity<CategoryDto> getsingle(@PathVariable("catid")String catid){
		
		CategoryDto getsinlgecategory = categoryService.getsinlgecategory(catid);
		return new ResponseEntity<CategoryDto>(getsinlgecategory,HttpStatus.OK)
;		
		
	}
//	get all
	@GetMapping
	
	public ResponseEntity<pagableResponse<CategoryDto>> getall(
			
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			@RequestParam(value = "pagesize",defaultValue = "5",required = false) int pagesize,
			@RequestParam(value = "sortby" ,defaultValue = "title",required = false) String sortby,
			@RequestParam(value = "sortdir",defaultValue = "asc",required = false) String sortdir
			){
		
		pagableResponse<CategoryDto> getallcategory = categoryService.getallcategory(pagenumber, pagesize, sortby, sortdir);
		return new ResponseEntity<pagableResponse<CategoryDto>>(getallcategory,HttpStatus.OK);
		
	}
	
	@PostMapping("/image/{catid}")
	public ResponseEntity<imageresponse> uploadcategoryimage(@RequestParam("catimage") MultipartFile catimage,@PathVariable("catid") String catid) throws IOException
	{
		 String uploadfile = fileservice.uploadfile(catimage, path);
		 
		    CategoryDto getsinlgecategory = categoryService.getsinlgecategory(catid);
		    getsinlgecategory.setCoverimage(uploadfile);
		    CategoryDto updatecategroy = categoryService.updatecategroy(getsinlgecategory, catid);
		    
		    imageresponse response= new imageresponse();
		    response.setImagename(uploadfile);
		    response.setMessage("uploaded sucess");
		    response.setStatus(HttpStatus.OK);
		    response.setSucess(true);
		    
		    
		
		return new ResponseEntity<imageresponse>(response,HttpStatus.OK);
		
	}
	@GetMapping("/image/{catid}")
	public void servecategoryimage(@PathVariable("catid") String catid,HttpServletResponse response) throws IOException {
		
		CategoryDto getsinlgecategory = categoryService.getsinlgecategory(catid);
		String coverimage = getsinlgecategory.getCoverimage();
	InputStream serveimage = fileservice.serveimage(path, coverimage);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(serveimage, response.getOutputStream());
		
	}
	
	@PostMapping("/{categoryid}/product")
	 public ResponseEntity<ProductDto> createproductwithcategory(
			 @PathVariable("categoryid") String categoryid,@RequestBody ProductDto dto
			 
			 ){
		ProductDto createproductwithcategory = productimp.createproductwithcategory(dto, categoryid);
		
	   return new ResponseEntity<ProductDto>(createproductwithcategory,HttpStatus.CREATED);
		
		
		 
		  
		 
	 }
	
	@PutMapping("/{catid}/product/{productid}")
	public ResponseEntity<ProductDto> assigncattoproduct(
			
			@PathVariable("catid") String catid ,
			@PathVariable("productid") String productid
			){
		
		
		  ProductDto assigncategorytoproduct = productimp.assigncategorytoproduct(catid, productid);
		  
				return new ResponseEntity<ProductDto>(assigncategorytoproduct,HttpStatus.OK                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     );
		
	}
	
	
	
@GetMapping("/{catid}/product")
	public ResponseEntity<pagableResponse<ProductDto>> getproductofcategroy(
			
			@PathVariable("catid") String catid,
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			@RequestParam(value = "pagesize",defaultValue = "5",required = false) int pagesize,
			@RequestParam(value = "sortby" ,defaultValue = "title",required = false) String sortby,
			@RequestParam(value = "sortdir",defaultValue = "asc",required = false) String sortdir
			){
		
		   pagableResponse<ProductDto> getproductofcategroy = productimp.getproductofcategroy(catid, pagenumber, pagesize, sortby, sortdir);
		   return new ResponseEntity<pagableResponse<ProductDto>>(getproductofcategroy,HttpStatus.OK);
	
		
	}
	
	
}
//}
//InputStream serveimage = this.fileservice.serveimage(path, imageName);
//
//response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//  StreamUtils.copy(serveimage, response.getOutputStream());



