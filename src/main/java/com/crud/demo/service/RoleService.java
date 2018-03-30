package com.crud.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.enums.RoleType;
import com.crud.demo.jpaRepositories.RoleJpaRepository;
import com.crud.demo.jpaRepositories.UserJpaRepository;
import com.crud.demo.model.Role;
import com.crud.demo.model.User;
@Service
public class RoleService {
	@Autowired
	private UserJpaRepository userJpaRepository;
		@Autowired
		private RoleJpaRepository roleJpaRepository;
		
		
		
	



	public String createRole(Role role) 
	{
		Role existingRole=roleJpaRepository.findByRoleType(role.getRoleType());
		if(existingRole==null)
		{ existingRole=new Role();
		 existingRole.setRoleType(role.getRoleType());
		roleJpaRepository.save(existingRole);
		return "Role added successfully";
		}	
		return "Role not added";
	  }

public void assignRole(Integer userId,String roleType) {
		User user=userJpaRepository.findOne(userId);
		if(user!=null)
			{ Set<Role> roles=new HashSet<>();
			  for(Role r:user.getRoles())
			  {
				  roles.add(r); 
			  }
			   Role existingRole=roleJpaRepository.findByRoleType(roleType);
			{
				if(existingRole!=null)
				{
				  
				  roles.add(existingRole);
				  user.setRoles(roles);
				  userJpaRepository.save(user);
					
				}
				 
			 }
		}
		
	}

}
