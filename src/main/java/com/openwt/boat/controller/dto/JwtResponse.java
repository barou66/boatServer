package com.openwt.boat.controller.dto;

import com.openwt.boat.repository.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {

    private String username;
    private Role role;
    private String token;
}