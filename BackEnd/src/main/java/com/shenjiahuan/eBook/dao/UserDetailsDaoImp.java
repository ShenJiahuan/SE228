package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDetailsDaoImp implements UserDetailsDao {

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
