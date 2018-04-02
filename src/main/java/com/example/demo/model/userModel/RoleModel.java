package com.example.demo.model.userModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="role")
//@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class)
public class RoleModel implements Serializable{

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private long id;
private String userRole;
public String getUserRole() {
	return userRole;
}

public void setUserRole(String userRole) {
	this.userRole = userRole;
}

@ManyToMany(mappedBy="role"	)
private List<UserModel> user=new ArrayList<>();

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

@JsonIgnore
public List<UserModel> getUser() {
	return user;
}

//@JsonIgnore
public void setUser(List<UserModel> user) {
	this.user = user;
}
}
