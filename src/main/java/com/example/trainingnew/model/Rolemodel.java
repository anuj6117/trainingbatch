package com.example.trainingnew.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import net.minidev.json.annotate.JsonIgnore;

@Entity
@Table(name="roles")
public class Rolemodel {
	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private long id;
	private String role;

	@ManyToMany(mappedBy="roles")

	private List<Usermodel> user=new ArrayList<>();

	public List<Usermodel> getUser() {
		return user;
	}

	public void setUser(List<Usermodel> user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}	
	
}
