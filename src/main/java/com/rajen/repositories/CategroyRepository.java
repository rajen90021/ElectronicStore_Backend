package com.rajen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rajen.entity.Category;

@Repository
public interface CategroyRepository extends JpaRepository<Category, String> {

}
	