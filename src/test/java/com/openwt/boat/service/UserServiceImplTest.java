package com.openwt.boat.service;

import com.openwt.boat.repository.Role;
import com.openwt.boat.repository.UserDaoRepository;
import com.openwt.boat.repository.entity.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {

    @MockBean
    private UserDaoRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    void loadUserByUsername_OK() {
        //Arrange
        doReturn(Optional.of(UserDao.builder()
                .username("username")
                .role(Role.ADMIN)
                .build())).when(userRepository)
                .findByUsername("username");
        //Act
        UserDetails user = userService.loadUserByUsername("username");

        //Assert
        assertNotNull(user);
        assertEquals("username", user.getUsername());
        assertEquals(Collections.singletonList(Role.ADMIN), user.getAuthorities());
    }

    @Test
    void loadUserByUsername_KO() {
        //AAA
        assertNull(userService.loadUserByUsername("username1"));
    }
}