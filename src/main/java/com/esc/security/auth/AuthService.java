package com.esc.security.auth;


import com.esc.security.dto.UserModelDTO;
import com.esc.security.dto.UserModelDTOMapper;
import com.esc.security.jwt.JWTUtil;
import com.esc.security.model.UserModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserModelDTOMapper userModelDTOMapper;

    public AuthService(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserModelDTOMapper userModelDTOMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userModelDTOMapper = userModelDTOMapper;
    }

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(), authRequest.password()
                )
        );
        UserModel principal = (UserModel) authentication.getPrincipal();
        UserModelDTO userModelDTO = userModelDTOMapper.apply(principal);
        String token = jwtUtil.issueToken(userModelDTO.email(), userModelDTO.roles());

        return new AuthResponse(token, userModelDTO);
    }
}
