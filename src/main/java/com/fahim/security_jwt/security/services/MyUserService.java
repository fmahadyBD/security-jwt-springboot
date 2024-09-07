package com.fahim.security_jwt.security.services;

import com.fahim.security_jwt.security.entity.MyUser;
import com.fahim.security_jwt.security.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserService {

    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public MyUser asave(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return myUserRepository.save(myUser);
    }

    public List<MyUser> findAll() {
        return myUserRepository.findAll();
    }
}
