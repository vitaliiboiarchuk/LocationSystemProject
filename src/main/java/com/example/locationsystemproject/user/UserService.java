package com.example.locationsystemproject.user;

public interface UserService {
    User findByUserName(String name);

    void saveUser(User user);
}
