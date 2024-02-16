package com.rajen.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajen.entity.Order;
import com.rajen.entity.Users;



public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUsers(Users user);

}
