package com.esc.security.auth;

public record AuthRequest(
        String email ,
        String password
){
}
