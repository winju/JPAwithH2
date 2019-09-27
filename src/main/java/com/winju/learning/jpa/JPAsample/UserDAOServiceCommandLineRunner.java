package com.winju.learning.jpa.JPAsample;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.winju.learning.jpa.JPAsample.entity.User;
import com.winju.learning.jpa.JPAsample.service.UserDAOService;
import com.winju.learning.jpa.JPAsample.service.UserRepository;

@Component
public class UserDAOServiceCommandLineRunner implements CommandLineRunner{
	
	@Autowired
	UserRepository userRepository;
//	UserDAOService userDaoService;
	
	private static final Logger log = LoggerFactory.getLogger(UserDAOServiceCommandLineRunner.class);
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		User user = new User("Jack", "Admin1");
//		long insert = userDaoService.insert(user);
		userRepository.save(user);
		log.info("First user is created : "+user);
		
		User user2 = new User("Jill", "Admin2");
		userRepository.save(user2);
		log.info("Second user is created : "+user2);
		
		Optional<User> userWithIdOne = userRepository.findById(1L);
		log.info("the user retreived from database : "+userWithIdOne);
		
		List<User> allUsers = userRepository.findAll();
		log.info("All the users from database : "+allUsers);
	
	}
	
}
