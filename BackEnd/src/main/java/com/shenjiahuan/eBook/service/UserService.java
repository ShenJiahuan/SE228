package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.dao.UserDao;
import com.shenjiahuan.eBook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserDao userDao;

    public boolean save(User user) {
        return userDao.save(user);
    }

    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    public boolean banUser(int uid, boolean banned) {
        return userDao.banUser(uid, banned);
    }

    public boolean adminUser(int uid, boolean admin) {
        return userDao.adminUser(uid, admin);
    }
}
