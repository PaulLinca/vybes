package com.vybes;

import com.vybes.model.Role;
import com.vybes.repository.RoleRepository;
import com.vybes.service.auth.AuthenticationService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VybesApplication {
    public static void main(String[] args) {
        SpringApplication.run(VybesApplication.class, args);
    }

    @Bean
    CommandLineRunner initUserRole(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByAuthority("USER").isPresent()) {
                return;
            }
            roleRepository.save(Role.builder().authority("USER").build());
        };
    }
}
