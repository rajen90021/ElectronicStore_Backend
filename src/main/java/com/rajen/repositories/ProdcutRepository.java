package com.rajen.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rajen.dtos.ProductDto;
import com.rajen.entity.Category;
import com.rajen.entity.Product;

@Repository
public interface ProdcutRepository extends JpaRepository<Product, String> {

	
	   Page<Product> findByTitleContaining(String subtitle,Pageable pageable);
	   Page<Product> findByLiveTrue(Pageable pageable);
	   
	   Page<Product> findByCategory(Category cat,Pageable pageable);
	
}
