package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.Role;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {
    public List<String> findRoleById(int userId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("Select role from Role where uid = :uid");
        query.setParameter("uid", userId);
        @SuppressWarnings("unchecked")
        List<String> roles = query.list();
        session.getTransaction().commit();
        return roles;
    }

    public void addRoleToUser(int userId, String role) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            session.save(new Role(userId, role));
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
