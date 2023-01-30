
package io.welldev.techbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class TechBoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechBoxApplication.class, args);
    }

}