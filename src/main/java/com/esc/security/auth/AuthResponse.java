package com.esc.security.auth;


import com.esc.security.dto.UserModelDTO;

public record AuthResponse (
        String token,
        UserModelDTO userModelDTO
){

}
