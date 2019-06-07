package com.shenjiahuan.eBook.dao.impl;

import com.shenjiahuan.eBook.dao.UserDao;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBanned((byte) 0);
        user.setAdmin((byte) 0);
        user.setRoot((byte) 0);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    // FIXME: only specific group of users can be banned
    public void banUser(int uid, boolean banned) {
        User user = userRepository.getOne(uid);
        user.setBanned(banned ? (byte) 1 : (byte) 0);
        userRepository.save(user);
    }

    @Override
    public void adminUser(int uid, boolean admin) {
        User user = userRepository.getOne(uid);
        user.setAdmin(admin ? (byte) 1 : (byte) 0);
        userRepository.save(user);
    }
}
