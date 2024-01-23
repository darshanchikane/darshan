package com.LoginSignUp;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.LoginSignUp.model.User;
import com.LoginSignUp.repository.UserRepository;

@SpringBootApplication
public class LoginSignUpApplication {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(LoginSignUpApplication.class, args);
	}

	// @PostConstruct
	// public void initUsers(){

	// 	List<User> users = 	Stream.of(
	// 		new User("1","darshan","darshan","darshuchikane46@gmail.com"),
	// 		new User("2","boppo","boppo","boppo@gmail.com")
	// 	).collect(Collectors.toList());

	// 	userRepository.saveAll(users);
	// }

}
