package com.rajen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajen.entity.OrderItem;



public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>
{
	
}