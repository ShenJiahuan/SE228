package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
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
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public User findUserByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "From User where email = :email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        @SuppressWarnings("unchecked")
        List<User> users = query.list();
        session.getTransaction().commit();
        return users.size() != 0 ? users.get(0) : null;
    }

    @Override
    public User findUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String hql = "From User where username = :username";
        Query query = session.createQuery(hql);
        query.setParameter("username", username);
        @SuppressWarnings("unchecked")
        List<User> users = query.list();
        session.getTransaction().commit();
        return users.size() != 0 ? users.get(0) : null;
    }

    @Override
    public List<User> findAllUsers() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("From User");
        @SuppressWarnings("unchecked")
        List<User> users = query.list();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public void banUser(int uid, boolean banned) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("update User set banned = :banned where uid = :uid");
            query.setParameter("banned", banned ? (byte) 1 : (byte) 0);
            query.setParameter("uid", uid);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void adminUser(int uid, boolean admin) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("update User set admin = :admin where uid = :uid");
            query.setParameter("admin", admin ? (byte) 1 : (byte) 0);
            query.setParameter("uid", uid);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
