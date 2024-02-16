package com.rajen.service.imp;

import java.io.FileNotFoundException;
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
import org.springframework.stereotype.Service;

import com.rajen.dtos.CategoryDto;
import com.rajen.dtos.UserDto;
import com.rajen.dtos.pagableResponse;
import com.rajen.entity.Category;
import com.rajen.entity.Users;
import com.rajen.exception.resourcenotfoundexception;
import com.rajen.repositories.CategroyRepository;
import com.rajen.service.CategoryService;


@Service
public class CategoryServiceimp implements CategoryService {
	
	@Autowired
	private CategroyRepository categroyRepository;
@Autowired
	private ModelMapper mapper;
@Value("${category.profile.image.path}")
private String catpath;

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
	
		
		   String randomid = UUID.randomUUID().toString();
		   categoryDto.setCategoryid(randomid);
		   
		   Category category = mapper.map(categoryDto, Category.class);
		   Category save = categroyRepository.save(category);
		   
		   
		
		return mapper.map(save, CategoryDto.class);
	}

	@Override
	public CategoryDto updatecategroy(CategoryDto categoryDto, String categoryid) {

		Category category =null;
		    Optional<Category> findById = categroyRepository.findById(categoryid);
		    
		    if(findById.isPresent()) {
		    	 category = findById.get();
		    }else {
		    	throw new resourcenotfoundexception("category not found ");
		    }
		    
		    category.setTitle(categoryDto.getTitle());
		    category.setDiscription(categoryDto.getDiscription());
		    category.setCoverimage(categoryDto.getCoverimage());
		    Category save = categroyRepository.save(category);
		
		return mapper.map(save, CategoryDto.class);
	}

	@Override
	public void delete(String categoryid) {
		
		Category category =null;
	    Optional<Category> findById = categroyRepository.findById(categoryid);
	    
	    if(findById.isPresent()) {
	    	 category = findById.get();
	    }else {
	    	throw new resourcenotfoundexception("category not found ");
	    }
	    
	    String userimage = category.getCoverimage();
	    String fullpath = catpath + userimage;

	    try {
	        Path path2 = Path.of(fullpath);

	        if (Files.exists(path2)) {
	            Files.delete(path2);
	        } else {
	            // Handle the case where the file does not exist
	            throw new FileNotFoundException("File not found: " + path2);
	        }
	    } catch (IOException e) {
	        // Handle the exception appropriately, e.g., log or throw a custom exception
	        e.printStackTrace();
	    }
			
	    categroyRepository.delete(category);
	}

	@Override
	public pagableResponse<CategoryDto> getallcategory(int pagenumber ,int pagesize,String sortby,String sortdir) {
		// TODO Auto-generated method stub
		
		 Sort sort = Sort.by(sortby);
		    
		    if (sortdir.equalsIgnoreCase("asc")) {
		        sort = sort.ascending();
		    } else {
		        sort = sort.descending();
		    }
		
		    Pageable pageable= PageRequest.of(pagenumber, pagesize,sort);
		    
		       Page<Category> page = categroyRepository.findAll(pageable);
		       
		       List<Category> content = page.getContent();
		       
		             List<CategoryDto> list = converttcategorydto(content);
		       
		           pagableResponse<CategoryDto> response= new pagableResponse<CategoryDto>();
		           response.setContent(list);
		           response.setPagenumber(page.getNumber());
		           response.setPageseize(page.getSize());
		           response.setTotalelement(page.getTotalElements());
		           response.setTotalpages(page.getTotalPages());
		           response.setLastpage(page.isLast());
		           
		return response;
	}
	
	
	
	public List<CategoryDto> converttcategorydto(List<Category> users){
		
		List<CategoryDto> listofcategorydto= new ArrayList<>();
		
		for( Category userss :users) {
			
			CategoryDto userDto = this.mapper.map(userss, CategoryDto.class);
			listofcategorydto.add(userDto);
		}
		return listofcategorydto;
		}

	@Override
	public CategoryDto getsinlgecategory(String categoryid) {
		Category category =null;
	    Optional<Category> findById = categroyRepository.findById(categoryid);
	    
	    if(findById.isPresent()) {
	    	 category = findById.get();
	    }else {
	    	throw new resourcenotfoundexception("category not found ");
	    }
	    
		return mapper.map(category, CategoryDto.class);
	}

}
