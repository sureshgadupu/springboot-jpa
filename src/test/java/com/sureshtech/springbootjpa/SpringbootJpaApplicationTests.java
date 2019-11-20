package com.sureshtech.springbootjpa;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.sureshtech.springbootjpa.entity.User;
import com.sureshtech.springbootjpa.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootJpaApplicationTests {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}
	
	
	@Test
	public  void saveUser() {
		
		User user = new User();		
		//user.setId(1L);
		user.setFirstName("abc");
		user.setLastName("def");
		user.setEmail("adc@sdf.com");
		user.setDisabled(false);
		user.setSalary(1200d);
		
		user = userRepository.save(user);
		System.out.println(user.getId());
		assertNotNull(user.getId());
		assertEquals("abc", user.getFirstName());
		
	}
	
	@Test
	public void testRecordCount() {
		
		 long count = userRepository.count();
		 assertEquals(5,count );
		
	}
	
	@Test
	public void findAllUsers() {
		List<User>  userList = userRepository.findAll();
		
		assertNotNull(userList);		
	}
	
	@Test
	public void findUserById() {
		
		Optional<User> user = userRepository.findById(Long.valueOf(4));
		assertNotNull(user.get().getId());
	}
	
	
	@Test
	public void findByUserName() {
		List<User> users = userRepository.findByFirstName("NareshGoel");
		assertNotNull(users);
	}
	
	@Test
	public void findByUserNameLike() {
		List<User> users = userRepository.findByFirstNameLike("Naresh");
		assertNotNull(users);
		assertEquals(3, users.size());
	}
	
	@Test
	public void findUsersSortedByName() {
		
		Sort.Order order = new Sort.Order(Sort.Direction.DESC, "firstName").ignoreCase();
		List<User> users =  userRepository.findAll(Sort.by(order));
		for (User user : users) {
			System.out.println(user.getId() +" : "+ user.getFirstName());
		}
		assertEquals("NareshGoel", users.get(0).getFirstName());
	}
	
	@Test
	public void findUsersSortedByFNameandLName() {
		
		Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "firstName").ignoreCase();
		Sort.Order order2 = new Sort.Order(Sort.Direction.ASC, "lastName").ignoreCase();
		
		List<User> users =  userRepository.findAll(Sort.by(order1,order2));
//		for (User user : users) {
//			System.out.println(user.getId() +" : "+ user.getFirstName() +": "+user.getLastName());
//		}
		assertEquals("abc", users.get(0).getFirstName());
	}
	
	@Test
	public void findPagedUsersSortedByName() {
		
		int size=2;
		int page =0;
		Sort.Order order = new Sort.Order(Sort.Direction.DESC, "firstName").ignoreCase();
		Pageable pageable = PageRequest.of(page, size, Sort.by(order));
		
		Page<User> userPage =  userRepository.findAll(pageable);
		System.out.println(userPage.getSize());
		
		System.out.println("Slice Number : " + userPage.getNumber());
		
		System.out.println("NumberOfElements : " + userPage.getNumberOfElements());
		
		System.out.println("Slice Number : " + userPage.getSize());
		
		System.out.println("Total Pages : " + userPage.getTotalPages());
		
		//System.out.println("Slice Number : " + userPage.getNumber());
		
		for (User user : userPage) {
			System.out.println(user.getId() +" : "+ user.getFirstName());
		}
		assertEquals(2,userPage.getSize());
	}
}
