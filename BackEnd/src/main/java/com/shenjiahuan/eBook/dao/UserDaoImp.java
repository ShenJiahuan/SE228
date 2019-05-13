package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean save(User user) {
        boolean success = true;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBanned((byte) 0);
        user.setAdmin((byte) 0);
        user.setRoot((byte) 0);
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return success;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public User findUserByEmail(String email) {
        List<User> users = null;
        Session session = sessionFactory.getCurrentSession();
        String hql = "From User where email = :email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        users = query.list();
        return (users != null && users.size() != 0) ? users.get(0) : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findUserByUsername(String username) {
        List<User> users = null;
        Session session = sessionFactory.getCurrentSession();
        String hql = "From User where username = :username";
        Query query = session.createQuery(hql);
        query.setParameter("username", username);
        users = query.list();
        return (users != null && users.size() != 0) ? users.get(0) : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        List<User> users = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("From User");
        users = query.list();
        return users;
    }

    @Override
    // FIXME: only specific group of users can be banned
    public boolean banUser(int uid, boolean banned) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update User set banned = :banned where uid = :uid");
        query.setParameter("banned", banned ? (byte) 1 : (byte) 0);
        query.setParameter("uid", uid);
        query.executeUpdate();
        return success;
    }

    @Override
    public boolean adminUser(int uid, boolean admin) {
        boolean success = true;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update User set admin = :admin where uid = :uid");
        query.setParameter("admin", admin ? (byte) 1 : (byte) 0);
        query.setParameter("uid", uid);
        query.executeUpdate();
        return success;
    }
}
