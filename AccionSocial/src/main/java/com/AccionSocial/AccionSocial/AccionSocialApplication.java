package com.AccionSocial.AccionSocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@ComponentScan(basePackages = {"Controller", "Service", "Implement", "Repository", "Entity"})
@EntityScan("Entity")
@EnableJpaRepositories("Repository")

public class AccionSocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccionSocialApplication.class, args);
	}

}
