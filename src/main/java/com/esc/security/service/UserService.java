package com.esc.security.service;

import com.esc.security.dao.UserDAO;
import com.esc.security.dto.UserModelDTO;
import com.esc.security.dto.UserModelDTOMapper;
import com.esc.security.exception.ResourceDuplicationException;
import com.esc.security.exception.ResourceNotFoundException;
import com.esc.security.jwt.JWTUtil;
import com.esc.security.model.UserModel;
import com.esc.security.model.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDAO userDAO;
    //    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final UserModelDTOMapper userModelDTOMapper;

    @Autowired
    public UserService(@Qualifier("JPA") UserDAO userDAO,
//                       PasswordEncoder passwordEncoder,
                       JWTUtil jwtUtil,
                       UserModelDTOMapper userModelDTOMapper) {
        this.userDAO = userDAO;
//        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userModelDTOMapper = userModelDTOMapper;
    }

    public List<UserModelDTO> getAllUsers() {
        return userDAO.selectAllUsers()
                .stream()
                .map(userModelDTOMapper)
                .collect(Collectors.toList());

    }

    public void insertCustomer(UserRegisterRequest userRegisterRequest) {
        String userEmail = userRegisterRequest.email();
        String jwtToken = jwtUtil.issueToken(userRegisterRequest.email(), userRegisterRequest.role());

        if (userDAO.userEmailExists(userEmail)) {  // Correct method name
            throw new ResourceDuplicationException("Email Already Taken");
        }

//        UserModel newCustomer = new UserModel(
//                userRegisterRequest.email(),
//                passwordEncoder.encode(userRegisterRequest.password())
//        );
        UserModel newUser = new UserModel(
                userRegisterRequest.name(),
                userRegisterRequest.email(),
                userRegisterRequest.password(),
                userRegisterRequest.role()
        );

        userDAO.insertUser(newUser);
    }

    public UserModelDTO userByEmailMapper(String email) {
        return userDAO.getUserByEmail(email)
                .map(userModelDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with email [%s] not found".formatted(email)
                ));
    }

    public UserModel userByEmail(String email) {
        return userDAO.getUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                "User with email [%s] not found".formatted(email)
        ));
    }

}

