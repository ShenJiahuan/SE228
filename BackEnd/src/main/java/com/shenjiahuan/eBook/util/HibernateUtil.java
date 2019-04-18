package com.shenjiahuan.eBook.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void initialize() {
        sessionFactory =
                new Configuration().configure().buildSessionFactory();
    }
}
