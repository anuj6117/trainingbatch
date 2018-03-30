package com.crud.demo.jpaRepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crud.demo.model.Role;

public interface RoleJpaRepository extends JpaRepository<Role,Integer>{
	/*@Query("select r.role from Role,User u,user_role ur where u.userName=?1 and ur.user_id=u.u_id")
	List<String> findRoleByUserName(String userName);*/
  Role findByRoleType(String roleType);
}
