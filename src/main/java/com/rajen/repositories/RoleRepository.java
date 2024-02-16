package com.rajen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajen.entity.Role;

public interface RoleRepository  extends JpaRepository<Role, String>{

}
