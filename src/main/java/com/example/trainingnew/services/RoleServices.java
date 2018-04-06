package com.example.trainingnew.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trainingnew.DTO.UserRoleDTO;
import com.example.trainingnew.model.Rolemodel;
import com.example.trainingnew.model.UserModel;
import com.example.trainingnew.reprository.RoleRepo;
import com.example.trainingnew.reprository.UserRepo;

@Service
public class RoleServices {

	@Autowired
	UserRepo userrepo;

	@Autowired
	RoleRepo rolerepo;

	public Rolemodel createRole(Rolemodel note) {

		Rolemodel model = rolerepo.findOneByRoleType(note.getRoleType());

		if (model != null) {
			throw new NullPointerException(" Already have " + note.getRoleType() + " role");
		}
		else {
			return rolerepo.save(note);
		}
	}

	// assign role
	public UserModel assignRole(UserRoleDTO roles) {

		UserModel up = userrepo.findOneByUserId(roles.getUserId());
		Rolemodel rr = rolerepo.findOneByRoleType(roles.getRoleType());
			List<Rolemodel> rolelist=up.getRoles();
			UserModel user=null;
			boolean flag=false;
			for(Rolemodel model:rolelist) {
				if(model.getRoleType().equals(roles.getRoleType())){
					flag=true;
					throw new NullPointerException("This role Already has assigned");
				}
			}
			if(flag==false) {
				if (up != null) {
					if (rr != null) {
						up.getRoles().add(rr);
						 user = userrepo.save(up);
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
