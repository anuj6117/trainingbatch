package com.example.adarsh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	private String roleType;

	/*
	 * public List<User> getUser() { return user; }
	 * 
	 * public void setUser(List<User> user) { this.user = user; }
	 */

	/*
	 * @OneToMany
	 * 
	 * @JsonManagedReference private List<User> user;
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}
