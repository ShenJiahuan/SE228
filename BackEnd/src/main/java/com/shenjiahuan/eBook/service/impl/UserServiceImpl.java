package com.shenjiahuan.eBook.service.impl;

import com.shenjiahuan.eBook.dao.UserDao;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.service.UserService;
import com.shenjiahuan.eBook.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUserByEmail(email);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
            builder.disabled(false);
            builder.password(user.getPassword());
            List<String> authorities = new ArrayList<>();
            if (user.getAdmin() == 1) {
                authorities.add("ROLE_ADMIN");
            }
            if (user.getRoot() == 1) {
                authorities.add("ROLE_ROOT");
            }
            if (user.getBanned() == 1) {
                authorities.add("ROLE_BANNED");
            } else {
                authorities.add("ROLE_NORMAL");
            }
            builder.authorities(Converter.asStrings(authorities.toArray()));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }

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
