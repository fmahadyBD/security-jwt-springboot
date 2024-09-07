package com.fahim.security_jwt.security.controllers;

import com.fahim.security_jwt.security.entity.LoginForm;
import com.fahim.security_jwt.security.entity.MyUser;
import com.fahim.security_jwt.security.services.JwtService;
import com.fahim.security_jwt.security.services.MyUserService;
import com.fahim.security_jwt.security.services.MyUserdetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyUserController {


    @Autowired
    private MyUserService myUserService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserdetailsService myUserdetailsService;




    @PostMapping("/registration")
    public MyUser createUser(@RequestBody final MyUser myUser) {
       myUserService.asave(myUser);
        return myUser;
    }

    @GetMapping("/admin/home")
    public List<MyUser> getAllUsers() {
        return myUserService.findAll();
    }

    @GetMapping("/")
    public List<MyUser> getUsers() {
        return myUserService.findAll();
    }


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));

        if (authentication.isAuthenticated()) {

            return jwtService.generateToken(myUserdetailsService.loadUserByUsername(loginForm.username()));
        } else {

            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    }


