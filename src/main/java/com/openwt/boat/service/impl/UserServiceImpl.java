package com.openwt.boat.service.impl;

import com.openwt.boat.repository.UserDaoRepository;
import com.openwt.boat.repository.entity.UserDao;
import com.openwt.boat.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDaoRepository userRepository;

    public UserServiceImpl(UserDaoRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDao loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDao> user = userRepository.findByUsername(username);
        return (user.isPresent()) ? user.get() : null;
    }

}
