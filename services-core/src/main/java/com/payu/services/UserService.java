package com.payu.services;

import com.payu.db.dao.UserRepository;
import com.payu.db.model.User;
import com.payu.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akesh.patil on 04-03-2017.
 */

@Service
public class UserService extends BaseService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return this.userRepository.getAllUsers();
    }

    public Integer createUser(User user) {
        return this.userRepository.createUser(user);
    }
}
