package br.com.devsdofuturobr.vendor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VendorNotFoundException extends ResponseStatusException {
    public VendorNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Vendor not found for this ID: " + id + ".");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
