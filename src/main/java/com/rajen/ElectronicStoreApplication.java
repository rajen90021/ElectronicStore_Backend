package com.rajen;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.rajen.entity.Role;
import com.rajen.repositories.RoleRepository;


@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {

	
	
	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}
	@Bean
	public ModelMapper mapper()
	{
		return new ModelMapper();
	}
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Autowired
	private RoleRepository rolerepository;
    String  normalid = "hdrbxvzsfujdhghg";
	   String  adminid = "afaefada";
	@Override
	public void run(String... args) throws Exception {
		System.out.println(encoder.encode("mummy123"));
		System.out.println(encoder.encode("rajen123"));
		
		 Role normal = new Role();
		    normal.setRoleid(normalid);
		    normal.setRolename("ROLE_NORMAL");

		    Role admin = new Role();
		    admin.setRoleid(adminid);  // Corrected this line
		    admin.setRolename("ROLE_ADMIN");

		    rolerepository.save(normal);
		    rolerepository.save(admin);
		
	}
}
