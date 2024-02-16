package com.rajen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rajen.dtos.ProductDto;
import com.rajen.dtos.pagableResponse;

@Service
public interface ProductService {

	
	
//	create 
	
	  ProductDto create(ProductDto dto);
	
//	update 
	
	  ProductDto update(ProductDto dto ,String productid);
	
	
//	delete 
	void delete(String productid);
	
	
//	get by id 
	
	ProductDto getbyid(String productid);
	
//	get all
pagableResponse<ProductDto> getall(int pagenumber, int pagesize,String sortby,String sortdir);
	
//	get all live 
pagableResponse<ProductDto> getalllive(int pagenumber, int pagesize,String sortby,String sortdir);
	
//	search product 
	
pagableResponse<ProductDto> searchproduct(String subtitle ,int pagenumber, int pagesize,String sortby,String sortdir);

  ProductDto  createproductwithcategory(ProductDto dto,String categoryid);
    ProductDto assigncategorytoproduct(String catergoryid,String productid);
    
    pagableResponse<ProductDto> getproductofcategroy(String catid,int pagenumber, int pagesize,String sortby,String sortdir);
}
