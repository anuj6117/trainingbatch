package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, Integer> {
	RoleModel findByRoleType(String roleType);
}
