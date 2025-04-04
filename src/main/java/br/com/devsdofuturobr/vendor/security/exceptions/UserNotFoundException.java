package br.com.devsdofuturobr.vendor.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "User not found with username: " + username);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
