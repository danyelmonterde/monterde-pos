package com.monterdev.monterdepos.util;

import com.monterdev.monterdepos.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

import static com.monterdev.monterdepos.constants.ConfigFile.CONFIG_FILE_NAME;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
               // InputStream ip = HibernateUtil.class.getClassLoader().getResourceAsStream("application-" + System.getProperty("ENV") + ".properties");
                InputStream ip = HibernateUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
                settings.load(ip);
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Item.class);
                configuration.addAnnotatedClass(Sales.class);
                configuration.addAnnotatedClass(SalesTransaction.class);
                configuration.addAnnotatedClass(Purchase.class);
                configuration.addAnnotatedClass(PurchaseTransaction.class);
                configuration.addAnnotatedClass(Category.class);

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
