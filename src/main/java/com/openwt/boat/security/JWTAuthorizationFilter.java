package com.openwt.boat.security;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import com.openwt.boat.service.JWTService;
import com.openwt.boat.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserService userService;
    private JWTService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager am) {
        super(am);
    }


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        String token = getJwt(request);

        if(token == null && cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName()
                        .equals("token")) {
                    token = cookie.getValue();
                }
            }
        }

        if(Objects.isNull(token)) {
            chain.doFilter(request, response);
            return;
        }

        if(Objects.isNull(jwtService)) {
            WebApplicationContext wac = getWebApplicationContext(request);
            if(Objects.nonNull(wac)) {
                jwtService = wac.getBean(JWTService.class);
            }
        }

        if(Objects.isNull(userService)) {
            WebApplicationContext wac = getWebApplicationContext(request);
            if(Objects.nonNull(wac)) {
                userService = wac.getBean(UserService.class);
            }
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private WebApplicationContext getWebApplicationContext(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.replace("Bearer ", "") : null;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String jwtToken) {
       try {
            String payload = jwtService.validateToken(jwtToken);
            JsonParser parser = JsonParserFactory.getJsonParser();
            Map<String, Object> payloadMap = parser.parseMap(payload);
            String username = payloadMap.get("user")
                    .toString();
            UserDetails user = userService.loadUserByUsername(username);
            HashSet<GrantedAuthority> auths = new HashSet<>(user.getAuthorities());

            return new UsernamePasswordAuthenticationToken(user, null, auths);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
