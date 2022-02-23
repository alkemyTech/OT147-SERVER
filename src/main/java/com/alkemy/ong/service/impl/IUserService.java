package com.alkemy.ong.service.impl;

import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IUserService implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public void deleteUserById(String id) {

        userRepo.deleteById(id);
    }

}
