package com.openwt.boat.controller.v1;

import com.openwt.boat.controller.abst.ApiResponse200;
import com.openwt.boat.controller.conts.ApiConstants;
import com.openwt.boat.controller.dto.JwtResponse;
import com.openwt.boat.repository.entity.UserDao;
import com.openwt.boat.security.SecurityHelper;
import com.openwt.boat.service.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = ApiConstants.API_VERSION_ONE + "basicAuth", produces = APPLICATION_JSON_VALUE)
@Tag(name = "AuthApis", description = "Auth - Apis")
public class AuthApis {

    private final JWTService jwtService;

    public AuthApis(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/JwtResponse")
    @Operation(summary = "basic authentication with username and password", operationId = "userIsValid")
    @ApiResponse200(content = {@Content(schema = @Schema(implementation = JwtResponse.class))})
    public ResponseEntity<JwtResponse> userIsValid(HttpServletResponse response) {
        JwtResponse jwtResponse = getJwtResponse(response);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private JwtResponse getJwtResponse(HttpServletResponse response) {
        UserDao currentUser = SecurityHelper.getCurrentUser();
        String token = jwtService.generateToken(currentUser.getUsername(), currentUser.getRole()
                .name());
        setCookie(response, token);
        JwtResponse jwtResponse = JwtResponse.builder()
                .username(currentUser.getUsername())
                .role(currentUser.getRole())
                .token(token)
                .build();
        return jwtResponse;
    }

    private void setCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/api");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1800);
        response.addCookie(cookie);
    }
}
