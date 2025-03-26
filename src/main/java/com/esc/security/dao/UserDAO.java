package com.esc.security.dao;
/////////Step 2 to create Repo


import com.esc.security.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<UserModel> selectAllUsers();

    void insertUser(UserModel userModel);

    boolean userEmailExists(String email);

    Optional<UserModel> getUserByEmail(String email);

}