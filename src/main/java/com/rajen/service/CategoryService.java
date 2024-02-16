package com.rajen.service;

import org.springframework.stereotype.Service;

import com.rajen.dtos.CategoryDto;
import com.rajen.dtos.pagableResponse;

@Service
public interface CategoryService {
	

//	create
	   public CategoryDto create(CategoryDto categoryDto);
	   
	   
	   
//	   update 
	   CategoryDto updatecategroy(CategoryDto categoryDto, String categoryid);
	   
//	   delte 
	   void delete(String categoryid);
	   
//	   get all 
	   
	   pagableResponse<CategoryDto> getallcategory(int pagenumber ,int pagesize,String sortby,String dirc);
	   
	   
//	   get single 
	   
	   CategoryDto getsinlgecategory(String categoryid);
	   
//	   search
	   
	   
	
}
