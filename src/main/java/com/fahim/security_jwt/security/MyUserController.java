package com.fahim.security_jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyUserController {
    @Autowired
    private MyUserService myUserService;

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

}
