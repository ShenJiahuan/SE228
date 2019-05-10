package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.User;

import java.util.List;

public interface UserDao {
    boolean save(User user);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    List<User> findAllUsers();

    boolean banUser(int uid, boolean banned);

    boolean adminUser(int uid, boolean admin);
}
