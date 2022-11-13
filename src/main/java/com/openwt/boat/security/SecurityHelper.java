package com.openwt.boat.security;


import com.openwt.boat.repository.entity.UserDao;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityHelper {

    private SecurityHelper() {
    }

    public static UserDao getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            return Objects.nonNull(auth) ? (UserDao) auth.getPrincipal() : null;
        } catch(Exception e) {
            return null;
        }

    }
}
