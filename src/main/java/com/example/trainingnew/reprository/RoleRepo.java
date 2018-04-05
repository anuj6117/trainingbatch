package com.example.trainingnew.reprository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trainingnew.model.Rolemodel;

public interface RoleRepo extends JpaRepository<Rolemodel, Long>{

	Rolemodel findOneByRoleId(long id);
	
	Rolemodel findOneByRoleType(String name);

	Rolemodel findOneByRoleType(List<Rolemodel> roles);

}
