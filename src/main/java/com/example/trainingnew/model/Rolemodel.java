package com.example.trainingnew.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import net.minidev.json.annotate.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="roles")
public class Rolemodel {
	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private Long roleId;
	@NotEmpty(message="RoleType Can't be null")
	@NotNull(message="RoleType Can't be null")
	@NotBlank(message="RoleType Can't be null")
	@Pattern(regexp = "[a-zA-z]*",message="Number not accepted")
	private String roleType;

	
	@ManyToMany(mappedBy="roles")
	@com.fasterxml.jackson.annotation.JsonIgnore
	private List<UserModel> user=new ArrayList();
	

	public List<UserModel> getUser() {
		return user;
	}

	public void setUser(List<UserModel> user) {
		this.user = user;
	}

	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	
	
}
