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

    public void save(User user) {
        userDao.save(user);
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

    public void banUser(int uid, boolean banned) {
        userDao.banUser(uid, banned);
    }

    public void adminUser(int uid, boolean admin) {
        userDao.adminUser(uid, admin);
    }
}
