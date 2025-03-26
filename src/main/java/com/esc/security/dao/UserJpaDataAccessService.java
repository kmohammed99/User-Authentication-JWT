package com.esc.security.dao;
/////////Step 3 to create Repo

import com.esc.security.model.UserModel;
import com.esc.security.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("JPA")
public class UserJpaDataAccessService implements UserDAO {
    @Autowired
    UserJPARepository userRepository;

    @Override
    public List<UserModel> selectAllUsers() {
        return userRepository.findAll();

    }

    @Override
    public void insertUser(UserModel userModel) {
        userRepository.save(userModel);
    }

    @Override
    public boolean userEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
