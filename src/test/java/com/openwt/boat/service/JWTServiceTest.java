package com.openwt.boat.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    @Test
    void generateValidateToken_ok() {
        String result = jwtService.generateToken("username", "role");
        assertNotNull(result);
        result = jwtService.validateToken(result);
        assertNotNull(result);
    }

    @Test
    void validateToken_ko() {
        assertNull(jwtService.validateToken("result"));
    }
}