package br.com.devsdofuturobr.vendor.security.service.impl;

import br.com.devsdofuturobr.vendor.security.AuthenticationService;
import br.com.devsdofuturobr.vendor.security.JwtService;
import br.com.devsdofuturobr.vendor.security.dto.request.CreateUserRequestDTO;
import br.com.devsdofuturobr.vendor.security.dto.request.LoginRequestDTO;
import br.com.devsdofuturobr.vendor.security.dto.response.LoginResponseDTO;
import br.com.devsdofuturobr.vendor.security.entity.Roles;
import br.com.devsdofuturobr.vendor.security.entity.User;
import br.com.devsdofuturobr.vendor.security.exceptions.UserAlreadyExists;
import br.com.devsdofuturobr.vendor.security.repositories.RolesRepository;
import br.com.devsdofuturobr.vendor.security.repositories.UserRepository;
import br.com.devsdofuturobr.vendor.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @Override
    public void create(CreateUserRequestDTO request, Set<Roles> rolesRequest) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new UserAlreadyExists();
        }
        Set<Roles> roles = rolesRepository.findAll().stream()
                .filter(role -> rolesRequest.stream().anyMatch(roleRequest -> roleRequest.getDescription().equals(role.getDescription())))
                .collect(Collectors.toSet());
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(roles)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authenticate = authenticationService.authenticate(request.username(), request.password());
        String accessToken = jwtService.generateToken(authenticate);
        return new LoginResponseDTO(accessToken);
    }
}
