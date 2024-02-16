package com.rajen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajen.entity.CartItem;

public interface CaritemRepository extends JpaRepository<CartItem, Integer> {

}
