package br.com.devsdofuturobr.vendor.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExists extends ResponseStatusException {
    public UserAlreadyExists() {
        super(HttpStatus.BAD_REQUEST, "User already exists");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
