package com.techelevator.dao;

import com.techelevator.model.User;
import java.util.List;

public interface UserDao {

    List<User> getUsers();

    List<User> getPublicUsers();

    User getUserById(int userId);

    User getUserByUsername(String username);

    User createUser(User newUser);
}
