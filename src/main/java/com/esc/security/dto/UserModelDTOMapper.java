package com.esc.security.dto;

import com.esc.security.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserModelDTOMapper implements Function<UserModel, UserModelDTO> {
    @Override
    public UserModelDTO apply(UserModel userModel) {
        return new UserModelDTO(
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail(),
                userModel.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
    }
}