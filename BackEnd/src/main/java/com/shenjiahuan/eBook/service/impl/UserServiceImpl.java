package com.shenjiahuan.eBook.service.impl;

import com.shenjiahuan.eBook.dao.UserDao;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public void banUser(int uid, boolean banned) {
        userDao.banUser(uid, banned);
    }

    @Override
    public void adminUser(int uid, boolean admin) {
        userDao.adminUser(uid, admin);
    }
}
