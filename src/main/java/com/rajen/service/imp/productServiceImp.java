package com.rajen.service.imp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
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
import com.rajen.dtos.ProductDto;
import com.rajen.dtos.pagableResponse;
import com.rajen.entity.Category;
import com.rajen.entity.Product;
import com.rajen.exception.resourcenotfoundexception;
import com.rajen.repositories.CategroyRepository;
import com.rajen.repositories.ProdcutRepository;
import com.rajen.service.ProductService;

@Service
public class productServiceImp implements ProductService {

	@Autowired
	private ProdcutRepository prodcutRepository;
	@Autowired
	private ModelMapper mapper;

	@Value("${product.profile.image.path}")
	private String imagepath;

	@Autowired
	private CategroyRepository categroyRepository;

	@Override
	public ProductDto create(ProductDto dto) {
		// TODO Auto-generated method stub

		String id = UUID.randomUUID().toString();

		dto.setId(id);
		dto.setAddeddate(new Date());

		Product product = mapper.map(dto, Product.class);

		Product save = prodcutRepository.save(product);

		return mapper.map(save, ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto dto, String productid) {
		Product product = null;
		Optional<Product> findById = prodcutRepository.findById(productid);

		if (findById.isPresent()) {
			product = findById.get();

		} else {
			throw new resourcenotfoundexception("product not found with given id " + productid);
		}

		product.setTitle(dto.getTitle());
		product.setStock(dto.isStock());
		product.setQuantity(dto.getQuantity());
		product.setPrice(dto.getPrice());
		product.setLive(dto.isLive());
		product.setDiscription(dto.getDiscription());
		product.setDiscount(dto.getDiscount());
		product.setProductimage(dto.getProductimage());
		Product save = prodcutRepository.save(product);

		return mapper.map(save, ProductDto.class);
	}

	@Override
	public void delete(String productid) {
		// TODO Auto-generated method stub

		Product product = null;
		Optional<Product> findById = prodcutRepository.findById(productid);

		if (findById.isPresent()) {
			product = findById.get();

		} else {
			throw new resourcenotfoundexception("product not found with given id " + productid);
		}

		String userimage = product.getProductimage();
		String fullpath = imagepath + userimage;

		try {
			Path path2 = Path.of(fullpath);

			if (Files.exists(path2)) {
				Files.delete(path2);
			} else {
				// Handle the case where the file does not exist
				throw new resourcenotfoundexception("File not found: " + path2);
			}
		} catch (IOException e) {
			// Handle the exception appropriately, e.g., log or throw a custom exception
			e.printStackTrace();
		}

		prodcutRepository.delete(product);
	}

	@Override
	public ProductDto getbyid(String productid) {
		Product product = null;
		Optional<Product> findById = prodcutRepository.findById(productid);

		if (findById.isPresent()) {
			product = findById.get();

		} else {
			throw new resourcenotfoundexception("product not found with given id " + productid);
		}

		return mapper.map(product, ProductDto.class);
	}

	@Override
	public pagableResponse<ProductDto> getall(int pagenumber, int pagesize, String sortby, String sortdir) {

		Sort sort = Sort.by(sortby);

		if (sortdir.equalsIgnoreCase("asc")) {
			sort = sort.ascending();
		} else {
			sort = sort.descending();
		}

		Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
		Page<Product> page = prodcutRepository.findAll(pageable);
		List<Product> content = page.getContent();

		List<ProductDto> listofproductdto = converttproductdto(content);

		pagableResponse<ProductDto> response = new pagableResponse<ProductDto>();

		response.setContent(listofproductdto);
		response.setTotalpages(page.getTotalPages());
		response.setTotalelement(page.getTotalElements());
		response.setPageseize(page.getSize());
		response.setPagenumber(page.getNumber());
		response.setLastpage(page.isLast());

		return response;
	}

	@Override
	public pagableResponse<ProductDto> getalllive(int pagenumber, int pagesize, String sortby, String sortdir) {
		Sort sort = Sort.by(sortby);

		if (sortdir.equalsIgnoreCase("asc")) {
			sort = sort.ascending();
		} else {
			sort = sort.descending();
		}

		Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
		Page<Product> page = prodcutRepository.findByLiveTrue(pageable);
		List<Product> content = page.getContent();

		List<ProductDto> listofproductdto = converttproductdto(content);

		pagableResponse<ProductDto> response = new pagableResponse<ProductDto>();

		response.setContent(listofproductdto);
		response.setTotalpages(page.getTotalPages());
		response.setTotalelement(page.getTotalElements());
		response.setPageseize(page.getSize());
		response.setPagenumber(page.getNumber());
		response.setLastpage(page.isLast());

		return response;
	}

	@Override
	public pagableResponse<ProductDto> searchproduct(String subtitle, int pagenumber, int pagesize, String sortby,
			String sortdir) {
		Sort sort = Sort.by(sortby);

		if (sortdir.equalsIgnoreCase("asc")) {
			sort = sort.ascending();
		} else {
			sort = sort.descending();
		}

		Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
		Page<Product> page = prodcutRepository.findByTitleContaining(subtitle, pageable);
		List<Product> content = page.getContent();

		List<ProductDto> listofproductdto = converttproductdto(content);

		pagableResponse<ProductDto> response = new pagableResponse<ProductDto>();

		response.setContent(listofproductdto);
		response.setTotalpages(page.getTotalPages());
		response.setTotalelement(page.getTotalElements());
		response.setPageseize(page.getSize());
		response.setPagenumber(page.getNumber());
		response.setLastpage(page.isLast());

		return response;

	}

//	****************************************************************************************************
	public List<ProductDto> converttproductdto(List<Product> products) {

		List<ProductDto> productdto = new ArrayList<>();

		for (Product productss : products) {

			ProductDto productdtoo = this.mapper.map(productss, ProductDto.class);
			productdto.add(productdtoo);
		}
		return productdto;
	}

	@Override
	public ProductDto createproductwithcategory(ProductDto dto, String categoryid) {
		Category category = null;
		Optional<Category> findById = categroyRepository.findById(categoryid);
		if (findById.isPresent()) {
			category = findById.get();
		} else {
			throw new resourcenotfoundexception("category id not found ");
		}
		String id = UUID.randomUUID().toString();

		dto.setId(id);
		dto.setAddeddate(new Date());

		Product product = mapper.map(dto, Product.class);
		product.setCategory(category);

		Product save = prodcutRepository.save(product);

		return mapper.map(save, ProductDto.class);

	}


	@Override
	public ProductDto assigncategorytoproduct(String catergoryid, String productid) {
		 Category category = null;
		    Product product = null;

		    Optional<Category> findById = categroyRepository.findById(catergoryid);
		    if (findById.isPresent()) {
		        category = findById.get();
		    } else {
		        throw new resourcenotfoundexception("Category ID not found");
		    }

		    Optional<Product> findById2 = prodcutRepository.findById(productid);
		    if (findById2.isPresent()) {
		        product = findById2.get();
		    } else {
		        throw new resourcenotfoundexception("Product ID not found");
		    }

		    product.setCategory(category);
		    Product savedProduct = prodcutRepository.save(product);

		    return mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public pagableResponse<ProductDto> getproductofcategroy(String catid,int pagenumber, int pagesize,String sortby,String sortdir) {
		 Category category = null;
		Optional<Category> findById = categroyRepository.findById(catid);
	    if (findById.isPresent()) {
	        category = findById.get();
	    } else {
	        throw new resourcenotfoundexception("Category ID not found");
	    }
	    
		Sort sort = Sort.by(sortby);

		if (sortdir.equalsIgnoreCase("asc")) {
			sort = sort.ascending();
		} else {
			sort = sort.descending();
		}
	       
	      Pageable pageable= PageRequest.of(pagenumber, pagesize,sort);
	      
	     Page<Product> pageofproduct = prodcutRepository.findByCategory(category,pageable);
	     List<Product> list = pageofproduct.getContent();
	     
	     pagableResponse<ProductDto> response = new pagableResponse<ProductDto>();
	     List<ProductDto> listofproductdto = converttproductdto(list);
			response.setContent(listofproductdto);
			response.setTotalpages(pageofproduct.getTotalPages());
			response.setTotalelement(pageofproduct.getTotalElements());
			response.setPageseize(pageofproduct.getSize());
			response.setPagenumber(pageofproduct.getNumber());
			response.setLastpage(pageofproduct.isLast());

			return response;
		    
	}

}
