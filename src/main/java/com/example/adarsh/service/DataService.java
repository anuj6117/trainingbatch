package com.example.adarsh.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adarsh.Dto.AssigneRole;
import com.example.adarsh.domain.Role;
import com.example.adarsh.domain.User;
import com.example.adarsh.repository.RoleRepository;
import com.example.adarsh.repository.UserRepository;

@Service
public class DataService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	public User saveUser(User user) {

		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Calendar.getInstance().getTime();
		String date = formatter.format(today);
		
		User user1 = new User();
		user1.setUserName(user.getUserName());
		user1.setCountry(user.getCountry());
		user1.setPhoneNumber(user.getPhoneNumber());
		user1.setEmail(user.getEmail());
		user1.setPassword(user.getPassword());
		user1.setPassword(user.getPassword());
		user1.setDate(date);
		return userRepository.save(user1);
		
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public List<User> asignRole(AssigneRole assigneRole) {
		Long[] userids = assigneRole.getUser();
		Role role = roleRepository.findOne(assigneRole.getId());
		if (role != null) {

			List<User> result = new ArrayList<>();
			for (Long id : userids) {
				User user = userRepository.findOne(id);
				if (user != null) {

					result.add(user);
				}

			}
			role.setUser(result);
			System.out.println("result........" + result);

			roleRepository.save(role);
			return result;
		}
		return null;
	}

	public List<User> getByRole(Long id) {
		Role role = roleRepository.findOne(id);
		return role.getUser();
	}
}
