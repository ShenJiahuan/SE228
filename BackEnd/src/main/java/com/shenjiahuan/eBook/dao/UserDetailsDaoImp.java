package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


  private static SessionFactory sessionFactory;
  static {
    try {
      sessionFactory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  @Override
  public void save(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.save(user);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  @Override
  public User findUserByEmail(String email) {
    Session session = sessionFactory.openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
    Root<User> user = criteriaQuery.from(User.class);
    Predicate p = builder.and(builder.equal(user.get("email"), email));
    criteriaQuery.where(p);
    List<User> users = session.createQuery(criteriaQuery).getResultList();
    session.close();
    return users.size() != 0 ? users.get(0) : null;
  }

  @Override
  public User findUserByUsername(String username) {
    Session session = sessionFactory.openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
    Root<User> user = criteriaQuery.from(User.class);
    Predicate p = builder.and(builder.equal(user.get("username"), username));
    criteriaQuery.where(p);
    List<User> users = session.createQuery(criteriaQuery).getResultList();
    session.close();
    return users.size() != 0 ? users.get(0) : null;
  }
}
