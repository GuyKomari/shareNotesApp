package com.sharenotes.app;

import com.sharenotes.app.models.user.Role;
import com.sharenotes.app.repositories.user.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main class
 */
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    /**
     * init the role repository
     * @param roleRepository
     * @return
     */
    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {
        return args -> {
            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }
        };
    }
}
