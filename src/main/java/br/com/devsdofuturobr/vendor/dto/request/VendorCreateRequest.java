package br.com.devsdofuturobr.vendor.dto.request;

public record VendorCreateRequest(
        String name,
        String address,
        String city,
        String state,
        String zip,
        String country
) {}

