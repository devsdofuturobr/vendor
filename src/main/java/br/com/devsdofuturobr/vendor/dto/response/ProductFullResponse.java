package br.com.devsdofuturobr.vendor.dto.response;

import java.math.BigDecimal;

public record ProductFullResponse(
        Long id,
        VendorShortResponse vendor,
        String name,
        BigDecimal price,
        String description
) {
}
