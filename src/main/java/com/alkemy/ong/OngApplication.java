package com.alkemy.ong;


import com.alkemy.ong.entity.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class OngApplication {



	public static void main(String[] args) {
		SpringApplication.run(OngApplication.class, args);
	}





}
