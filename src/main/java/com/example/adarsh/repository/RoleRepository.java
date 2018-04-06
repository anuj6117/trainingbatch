package com.example.adarsh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adarsh.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Serializable> {

	Role findOneByRoleType(String roleName);

}
