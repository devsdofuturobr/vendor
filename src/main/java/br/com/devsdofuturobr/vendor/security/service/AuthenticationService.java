package br.com.devsdofuturobr.vendor.security;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    Authentication authenticate(String username, String password);
}
