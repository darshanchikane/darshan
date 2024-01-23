package com.LoginSignUp.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LoginSignUp.model.AuthRequestDto;
import com.LoginSignUp.model.RegistrationDto;
import com.LoginSignUp.model.User;
import com.LoginSignUp.model.UserResponseDto;
import com.LoginSignUp.repository.UserRepository;
import com.LoginSignUp.service.EmailService;
import com.LoginSignUp.service.OtpService;
import com.LoginSignUp.specification.UserSpecification;
import com.LoginSignUp.utils.ClassUtil;
import com.LoginSignUp.utils.JwtUtil;

@RestController
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    OtpService otpService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to Website !!!";
    }

    @GetMapping("/list")
    public Page<UserResponseDto> getUsers(Pageable pageable, @RequestParam(required = false) String search, @RequestParam(required = false) int pageNumber) {
        Page<User> userPage;

        int pageSize = 1;
        

        if (search != null && !search.isEmpty()) {

            if(pageNumber > 0 ) {
                userPage = userRepository.findAll(UserSpecification.searchUsers(search), PageRequest.of(pageNumber, pageSize));
            } else {
                userPage = userRepository.findAll(UserSpecification.searchUsers(search), PageRequest.of(0, pageSize));

            }
        } else {
            userPage = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        }

        Page<UserResponseDto> userResponseDtoPage = userPage.map(user -> {
            try {
                return ClassUtil.convertOneToAnother(user, UserResponseDto.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });

        return userResponseDtoPage;
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequestDto authRequestDto) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),
                    authRequestDto.getPassword()));

        } catch (Exception e) {
            throw new Exception("Invalid Username or Password");
        }

        return jwtUtil.generateToken(authRequestDto.getUsername());
    }

    @PostMapping("/save/user")
    public String registerUser(@RequestBody RegistrationDto registrationDto) {

        User user = userRepository.findByUsername(registrationDto.getUsername());

        try {

            if (user == null) {
                User u = new User();
                u.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                u.setEmail(registrationDto.getEmail());
                String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
                System.out.println("Original Password: " + registrationDto.getPassword());
                System.out.println("Encoded Password: " + encodedPassword);

                u.setPassword(encodedPassword);
                u.setUsername(registrationDto.getUsername());
                userRepository.save(u);
                return "User Added  ";
            } else {
                return "User Already Exists";

            }
        } catch (Exception e) {
            return "";
        }

    }

    @GetMapping("/forgot/password")
    public String forgotPassword(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        String subject = "Reset Password";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            User user = userRepository.findByUsername(username);

            if (user != null) {

                String password = otpService.generateOTP();
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                String body = "Hello, This is your new password -> " + password;
                emailService.sendSimpleEmail(user.getEmail(), subject, body);
            } else {
                return "User not found";
            }

        } else {
            return "Invalid or missing Authorization header";
        }

        return "Password reset email sent successfully!";
    }

}
