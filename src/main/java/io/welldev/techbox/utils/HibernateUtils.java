package io.welldev.techbox.utils;


import io.welldev.techbox.authentication.configuration.jwt.entity.Jwt;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.entity.User;
import io.welldev.techbox.userProductJoin.entity.UserProduct;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateUtils {

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        //create configuration
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/ProjectV1");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "Feelslikedope");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            properties.put(Environment.SHOW_SQL, true);

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