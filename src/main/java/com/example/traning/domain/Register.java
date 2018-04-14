package com.example.traning.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Training")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdOn"}, allowGetters = true)
public class Register {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	@Pattern(regexp = "[a-zA-z]*",message="UserName Number and special charecter not accepted")
	@Size(min=3,max=20,message="lenght must be 2 to 20")
	private String userName;
	@Email
	private String email;
	private boolean status=false;
	@Size(min=3,max=20,message="country lenght must be 3 to 20")
	private String country;
	
	private String password;
	@Column(nullable = false, updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	@Size(min=10,max=10,message="phoneNumber lenght must be 10 digit")
	private String phoneNumber;
	
	public Register(Register register) {
		super();
		this.userId = register.getuserId();
		this.userName = register.getUserName();
		this.email =register.getEmail();
		this.status = register.getStatus();
		this.country =register.getCountry();
		this.password =register.getPassword();
		this.createdOn = register.getCreatedOn();
		this.phoneNumber =register.getphoneNumber();
	}


    public Register() {}
	
	//======================Mapping=====================
	
    
  //{******************************************************}
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "register")
    private Set<Order> order = new HashSet<>();
   
    public Set<Order> getOrder() {
		return order;
	}


	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	//=====================================================
	@ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
	 @JoinTable(name = "users_roles",
     joinColumns = { @JoinColumn(name = "users_id") },
     inverseJoinColumns = { @JoinColumn(name = "roles_id") })
    private List<Role> roles = new ArrayList<>();
	
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	//==========================================================

	@OneToMany(cascade = CascadeType.ALL,
	            fetch = FetchType.LAZY,
	            mappedBy = "register")
	    private List<Wallet> wall=new ArrayList<>();
	
	 public List<Wallet> getWall() {
			return wall;
		}

		public void setWall(List<Wallet> wall) {
			this.wall = wall;
		}
	
	 
	 //==============================================================
	
	
	
	

	public long getuserId() {
		return userId;
	}

	public void setuserId(long userId) {
		this.userId=userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getphoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNo(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
