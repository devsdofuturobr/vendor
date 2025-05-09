package br.com.devsdofuturobr.vendor.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotNull Long vendorId,
        @NotNull @NotBlank @Size(min = 3, max = 100) String name,
        @NotNull BigDecimal price,
        String description
        ) {
}
