package br.com.devsdofuturobr.vendor.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VendorUpdateRequest(
        @NotNull Long id,
        @Size(max = 100) String name,
        @Size(max = 255) String address,
        @Size(max = 100) String city,
        @Size(max = 50) String state,
        @Size(max = 20) String zip,
        @Size(max = 50) String country
){}
