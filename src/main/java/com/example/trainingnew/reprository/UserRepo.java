package com.example.trainingnew.reprository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.trainingnew.model.UserModel;

public interface UserRepo extends JpaRepository<UserModel ,Integer>{

	UserModel findOneByUserId(Integer id);

	UserModel findOneByEmail(String email);

	UserModel findByUserName(String username);

	UserModel findAllByEmail(String email);

	UserModel findOneByUserName(String username);

	Optional<UserModel> findByEmail(String email);
	

	List<UserModel> findByStatusTrue();

	List<UserModel> findByStatusFalse();

	List<UserModel>findByUserName(String username, org.springframework.data.domain.Pageable page);
	
	
	 @Query(
	            value = "SELECT * FROM users WHERE user_name LIKE %:searchTerm%",
	            nativeQuery = true
	    )
	    public List<UserModel> searchWithNativeQuery(@Param("searchTerm") String searchTerm);

	public UserModel findByUserId(Long userId);

	UserModel findByPhoneNumber(String phoneNumber);

	UserModel findByUserId(Integer userId);

	UserModel findByWallets(String coinName);

	UserModel findByUserNameAndPhoneNumber(String userName, String phoneNumber);

	UserModel findAllByUserNameAndPhoneNumber(String userName, String phoneNumber);

}
