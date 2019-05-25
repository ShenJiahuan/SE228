package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(User user);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    List<User> findAllUsers();

    void banUser(int uid, boolean banned);

    void adminUser(int uid, boolean admin);
}
