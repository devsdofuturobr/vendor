package br.com.devsdofuturobr.vendor.security.config;

import br.com.devsdofuturobr.vendor.security.dto.enums.RolesEnum;
import br.com.devsdofuturobr.vendor.security.dto.request.CreateUserRequestDTO;
import br.com.devsdofuturobr.vendor.security.entity.Roles;
import br.com.devsdofuturobr.vendor.security.repositories.UserRepository;
import br.com.devsdofuturobr.vendor.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class AdminConfig implements CommandLineRunner {

    @Value("${first-user-admin.username}")
    private String username;

    @Value("${first-user-admin.password}")
    private String password;

    private final UserRepository userRepository;
    
    private final UserService userService;

    @Override
    public void run(String... args) {
        if (userRepository.existsByRolesDescription(RolesEnum.ADMIN.name())) {
            log.info("Admin user already exists!");
            return;
        }
        log.info("Creating admin user...");
        Set<Roles> roles = Set.of(new Roles(RolesEnum.ADMIN.name()));
        userService.create(new CreateUserRequestDTO(username, password), roles);
    }
}
