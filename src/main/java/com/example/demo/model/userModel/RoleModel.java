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

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="role")
//@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class)
public class RoleModel {

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private long roleId;
@NotEmpty(message="not empty")
private String roleType;

public String getRoleType() {
	return roleType;
}

public void setRoleType(String roleType) {
	this.roleType = roleType;
}

@ManyToMany(mappedBy="role"	)
private List<UserModel> user=new ArrayList<>();



public long getRoleId() {
	return roleId;
}

public void setRoleId(long roleId) {
	this.roleId = roleId;
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
