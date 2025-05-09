package br.com.devsdofuturobr.vendor.dto.response;

public record VendorFullResponse(
        Long id,
        String name,
        String address,
        String city,
        String state,
        String zip,
        String country
) {}