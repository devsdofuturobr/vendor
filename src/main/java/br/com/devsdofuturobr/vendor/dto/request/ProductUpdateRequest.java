package br.com.devsdofuturobr.vendor.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        @NotNull Long id,
        @Size(max = 100) String name,
        BigDecimal price,
        String description
) {
}
