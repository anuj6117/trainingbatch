package com.example.trainingnew.services;

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

		if (up != null) {

			if (rr != null) {
				up.getRoles().add(rr);
				UserModel u = userrepo.save(up);
				return u;
			}

			else {
				throw new NullPointerException("User role doesn't exist");
			}
		} else {
			throw new NullPointerException("User id doesn't exist");
		}

	}
}
