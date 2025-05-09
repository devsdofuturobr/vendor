package br.com.devsdofuturobr.vendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductNotFoundException extends ResponseStatusException {
    public ProductNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Product not found for this ID: " + id + ".");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
