package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDetailsDaoImp implements UserDetailsDao {

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean save(User user) {
        boolean success = true;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBanned((byte) 0);
        user.setAdmin((byte) 0);
        user.setRoot((byte) 0);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            success = false;
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return success;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findUserByEmail(String email) {
        List<User> users = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            String hql = "From User where email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            users = query.list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return (users != null && users.size() != 0) ? users.get(0) : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findUserByUsername(String username) {
        List<User> users = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            String hql = "From User where username = :username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            users = query.list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return (users != null && users.size() != 0) ? users.get(0) : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        List<User> users = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("From User");
            users = query.list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    // FIXME: only specific group of users can be banned
    public boolean banUser(int uid, boolean banned) {
        boolean success = true;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("update User set banned = :banned where uid = :uid");
            query.setParameter("banned", banned ? (byte) 1 : (byte) 0);
            query.setParameter("uid", uid);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            success = false;
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean adminUser(int uid, boolean admin) {
        boolean success = true;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("update User set admin = :admin where uid = :uid");
            query.setParameter("admin", admin ? (byte) 1 : (byte) 0);
            query.setParameter("uid", uid);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            success = false;
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
        return success;
    }
}
