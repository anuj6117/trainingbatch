package com.crud.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.enums.RoleType;
import com.crud.demo.jpaRepositories.RoleJpaRepository;
import com.crud.demo.jpaRepositories.UserJpaRepository;
import com.crud.demo.model.Role;
import com.crud.demo.model.User;
@Service
public class RoleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserJpaRepository userJpaRepository;
		@Autowired
		private RoleJpaRepository roleJpaRepository;
		
		
		
	



	public Map<String, Object> createRole(Role role) 
	{ 
		Map<String, Object> map= new HashMap<>();
		boolean isSuccess = false;
		Role existingRole=roleJpaRepository.findByRoleType(role.getRoleType());
		if(existingRole==null)
		{ existingRole=new Role();
		 existingRole.setRoleType(role.getRoleType());
		roleJpaRepository.save(existingRole);
		map.put("Result", "Role created successfully");
		map.put("isSuccess", true);
		LOGGER.info("Message on service::::::::::::::::::Role successfully added");
		}	
		else
		{
			map.put("Result", "Role already exist ");
			map.put("isSuccess", isSuccess);
		LOGGER.error("Message on service::::::::::::::::::Role not created(Already exist) ");
		}
		return map;
	  }

public Map<String, Object> assignRole(Integer userId,String roleType) {
	Map<String, Object> map= new HashMap<>();
	boolean isSuccess = false;
		User user=userJpaRepository.findOne(userId);
		Role comingRole=roleJpaRepository.findByRoleType(roleType);
		if(user!=null&&comingRole!=null)
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
			map.put("Result", "Role assigned successfully");
			map.put("isSuccess", true);
			LOGGER.info("Message on service::::::::::::::::::Role successfully assigned");
		}
		else
		{
			map.put("Result", "Role already assigned or doesnot exist");
			map.put("isSuccess", isSuccess);	
			LOGGER.error("Message on service::::::::::::::::::Role already assigned or doesnot exist");
		}
		return map;
	}

}
