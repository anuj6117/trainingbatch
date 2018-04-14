package com.example.traning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.traning.domain.Register;
import com.example.traning.domain.Role;
import com.example.traning.dto.UserRoleDto;
import com.example.traning.exception.UserNotFoundException;
import com.example.traning.repository.RegisterRepository;
import com.example.traning.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository rolerepository;
	@Autowired
	RegisterRepository registerrepository;

	//========================OPERATIONS==========================
	
	
	public List<Role> getAllNotes(){
		return rolerepository.findAll();
	}



	public Role createrole(Role role) throws UserNotFoundException {
		Role co=rolerepository.findByRoleType(role.getRoleType());
		System.out.println("hello 2"+co);
		if(co!=null) {
			throw new UserNotFoundException("this role already exist");
		}else {
			System.out.println("hello 3"+co);
			rolerepository.save(role);
		
		}
		return role;
	}
	
	public Register insertDataWithRole(UserRoleDto userroledto) {
		System.out.println(userroledto);
		Register up=registerrepository.findByUserId(userroledto.getUserid());
		Role rr=rolerepository.findByRoleType(userroledto.getRoleType());
		System.out.println(userroledto.getUserid());
		
		
		
			List<Role> rolelist=up.getRoles();
			Register user=null;
			boolean flag=false;
			for(Role model:rolelist) {
				if(model.getRoleType().equals(userroledto.getRoleType())){
					flag=true;
					throw new NullPointerException("This role Already has assigned");
				}
			}
			if(flag==false) {
				if (up != null) {
					if (rr != null) {
						up.getRoles().add(rr);
						 user = registerrepository.save(up);
						return user;
					}
					else {
						throw new NullPointerException("User role doesn't exist");
					}
				} else {
					throw new NullPointerException("User id doesn't exist");
				}
			}	
			return user;
		
		}
		
		
	}
	
	
	
	
	//========================OPERATIONS==========================
	


