package br.com.devsdofuturobr.vendor.security.service;

import br.com.devsdofuturobr.vendor.security.dto.request.CreateUserRequestDTO;
import br.com.devsdofuturobr.vendor.security.dto.request.LoginRequestDTO;
import br.com.devsdofuturobr.vendor.security.dto.response.LoginResponseDTO;
import br.com.devsdofuturobr.vendor.security.entity.Roles;

import java.util.Set;

public interface UserService {
    void create(CreateUserRequestDTO request, Set<Roles> rolesRequest);

    LoginResponseDTO login(LoginRequestDTO request);
}
