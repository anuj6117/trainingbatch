package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.constant.RoleEnum;
import com.example.demo.model.RoleModel;
import com.example.demo.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;

	public List<RoleModel> getAllDetails() {
		List<RoleModel> roleDetails = new ArrayList<RoleModel>();
		roleRepo.findAll().forEach(roleDetails::add);
		return roleDetails;

	}

	public Object addRole() {
		int i = 1;
		RoleModel roleModel = new RoleModel();
		for (RoleEnum type : RoleEnum.values()) {
			System.out.println(type);
			roleModel.setRoleId(i);
			roleModel.setRole(type.toString());
			roleRepo.save(roleModel);
			i++;
		}

		return "success";

	}

	public Object addRole1(RoleModel roleModel) throws Exception {
		if (roleModel.getRole().equals("")) {
			throw new Exception("Role type Cannot be null");
		} else {
			roleRepo.save(roleModel);
		}
		return "success";

	}

	public void deleteUser(int id) {
		roleRepo.deleteById(id);
	}

	public void updateUser(String id, RoleModel model) {
		roleRepo.save(model);
	}

}
