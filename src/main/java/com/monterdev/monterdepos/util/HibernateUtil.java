package com.monterdev.monterdepos.util;

import com.monterdev.monterdepos.model.Item;
import com.monterdev.monterdepos.model.Sales;
import com.monterdev.monterdepos.model.SalesTransaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                InputStream ip= HibernateUtil.class.getClassLoader().getResourceAsStream("application-"+System.getenv().get("ENV")+".properties");
                settings.load(ip);
//                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
//                settings.put(Environment.URL, "jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_a946ea8b949cf70?&serverTimezone=UTC&useSSL=false");
//                settings.put(Environment.USER, "b7d40f13f81591");
//                settings.put(Environment.PASS, "1b8278fd");
//                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
//                settings.put(Environment.SHOW_SQL, "true");
//                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//                settings.put(Environment.HBM2DDL_AUTO, "none");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Item.class);
                configuration.addAnnotatedClass(Sales.class);
                configuration.addAnnotatedClass(SalesTransaction.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return sessionFactory;
    }
}
