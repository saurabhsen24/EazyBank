package com.eazybank.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScans({ @ComponentScan("com.eazybank.springsecurity.controller"), @ComponentScan("com.eazybank.springsecurity.config")})
@EnableJpaRepositories("com.eazybank.springsecurity.repository")
@EntityScan("com.eazybank.springsecurity.entities")
public class SpringsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}

}
