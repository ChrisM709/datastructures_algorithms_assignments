package org.backendapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.backendapi")  
@EnableJpaRepositories(basePackages = "org.backendapi.repository") 
@EntityScan(basePackages = "org.backendapi.model")               
public class TreeApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TreeApiApplication.class, args);
    }
}
