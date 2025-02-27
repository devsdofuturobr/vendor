package br.com.devsdofuturobr.vendor.dto.response;

import java.math.BigDecimal;

public interface ProductProjectionResponse {
    Long getId();
    String getName();
    BigDecimal getPrice();
    String getDescription();
}
