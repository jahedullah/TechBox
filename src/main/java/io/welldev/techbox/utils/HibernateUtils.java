package io.welldev.techbox.utils;


import io.welldev.techbox.authentication.configuration.jwt.entity.Jwt;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.entity.User;
import io.welldev.techbox.userProductJoin.entity.UserProduct;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

public class HibernateUtils {

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        //create configuration
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(URL, "jdbc:mysql://127.0.0.1:3306/ProjectV1");
            properties.put(USER, "root");
            properties.put(PASS, "Feelslikedope");
            properties.put(DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(HBM2DDL_AUTO, "update");
            properties.put(SHOW_SQL, true);
            properties.put(FORMAT_SQL, true);

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(UserProduct.class);
            configuration.addAnnotatedClass(Jwt.class);


            //create session factory
            StandardServiceRegistry standardServiceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);
        }
        return sessionFactory;
    }
}