package br.com.devsdofuturobr.vendor.dto.response;

import java.math.BigDecimal;

public record ProductCreatedResponse(
        Long id,
        String name,
        BigDecimal price,
        String description
) {}
