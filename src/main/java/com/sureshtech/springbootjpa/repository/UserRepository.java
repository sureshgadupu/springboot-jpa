package com.sureshtech.springbootjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sureshtech.springbootjpa.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findByFirstName(String firstName);
	
	
	public List<User> findByFirstNameLike(String firstName);
	
	@Query("select u from User u where u.lastName like %:lasttName%")
	public List<User> searchByLastName(String lasttName);
	

}
